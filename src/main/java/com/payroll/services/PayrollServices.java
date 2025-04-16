package com.payroll.services;

import com.payroll.Exceptions.EmployeePayrollException;
import com.payroll.dtos.EmployeePayrollDTOS;
import com.payroll.dtos.PayrollAnalysisDTO;
import com.payroll.mapping.ToEmployeePayrollDto;
import com.payroll.mapping.ToPayrollAnalysisDto;
import com.payroll.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollServices {

    // returns us list of all employees
    public static List<EmployeePayrollDTOS> getEmployeePayrolls() throws EmployeePayrollException {
        List<EmployeePayrollDTOS> employeePayrolls = new ArrayList<>();

        String query = """
            SELECT *
            FROM employee e
            JOIN department d ON e.dept_id = d.dept_id
            LEFT JOIN contact c ON e.id = c.employee_id
            LEFT JOIN payroll p ON e.id = p.employee_id
        """;

        try (Connection conn = DbService.getInstance().getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                employeePayrolls.add(ToEmployeePayrollDto.map(rs));
            }
        } catch (Exception e) {
            throw new EmployeePayrollException(e.toString());
        }
        return employeePayrolls;
    }


    // Returns the payroll details of the employee with the specified employee ID
    public static EmployeePayrollDTOS getEmployeePayroll(int employee_id) throws EmployeePayrollException {
        EmployeePayrollDTOS employeePayrollDTOS=null;

        String query = """
            SELECT *
            FROM employee e
            JOIN department d ON e.dept_id = d.dept_id
            LEFT JOIN contact c ON e.id = c.employee_id
            LEFT JOIN payroll p ON e.id = p.employee_id
            WHERE e.id = ?
        """;
        try (Connection conn = DbService.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employee_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employeePayrollDTOS = ToEmployeePayrollDto.map(rs);
                break;
            }
        }
        catch (Exception e){
            throw new EmployeePayrollException(e.toString());
        }
        return employeePayrollDTOS;
    }

    //

    public static void updateEmployeeSalary(String name, double salary) throws EmployeePayrollException {
        String selectQuery = """
        SELECT p.salary 
        FROM payroll p
        JOIN employee e ON p.payroll_id = e.id 
        WHERE e.name = ?
    """;

        String updateQuery = """
        UPDATE payroll 
        SET salary = ? 
        WHERE payroll_id = (SELECT id FROM employee WHERE name = ?)
    """;

        try (Connection conn = DbService.getInstance().getConnection()) {
            // Step 1: Fetch and print previous salary
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, name);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    double oldSalary = rs.getDouble("salary");
                    System.out.println("Previous Salary of " + name + ": " + oldSalary);
                } else {
                    System.out.println("Employee not found with name: " + name);
                    return;
                }
            }

            // Step 2: Update salary
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setDouble(1, salary);
                updateStmt.setString(2, name);
                updateStmt.executeUpdate();
                System.out.println("Salary updated successfully.");
            }

            // Step 3: Fetch and print updated salary
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, name);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    double updatedSalary = rs.getDouble("salary");
                    System.out.println("Updated Salary of " + name + ": " + updatedSalary);
                }
            }
        } catch (Exception e) {
            throw new EmployeePayrollException(e.getMessage());
        }
    }

    public static List<EmployeePayrollDTOS> getEmployeesByDateRange(Date start , Date end ) throws EmployeePayrollException {

        List<EmployeePayrollDTOS> employeePayrolls = new ArrayList<>();

        String query = """
        SELECT * FROM employee e
        JOIN department d ON e.dept_id = d.dept_id
        LEFT JOIN contact c ON e.id = c.employee_id
        LEFT JOIN payroll p ON e.id = p.employee_id
        WHERE e.start_date BETWEEN ? AND ?
    """;

        try (Connection conn = DbService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, start);
            stmt.setDate(2, end);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employeePayrolls.add(ToEmployeePayrollDto.map(rs));
                }
            }

        } catch (Exception e) {
            throw new EmployeePayrollException("Error retrieving employees by date range: " + e.getMessage());
        }
        return employeePayrolls;
    }

    public static List<PayrollAnalysisDTO> getPayrollAnalysisByGender() throws EmployeePayrollException {
        List<PayrollAnalysisDTO> analysisList = new ArrayList<>();

        String query = """
        SELECT gender,
               SUM(salary) AS total_salary,
               AVG(salary) AS average_salary,
               MIN(salary) AS min_salary,
               MAX(salary) AS max_salary,
               COUNT(*) AS employee_count
        FROM employee e
        JOIN payroll p ON e.id = p.employee_id
        GROUP BY gender
    """;

        try (Connection conn = DbService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                analysisList.add(ToPayrollAnalysisDto.map(rs));
            }

        } catch (SQLException e) {
            throw new EmployeePayrollException("Error fetching payroll analysis: " + e.getMessage());
        }

        return analysisList;
    }
    public static void addEmployeeWithDetails(Employee employee, Contacts contact, Payroll payroll) throws EmployeePayrollException {
        String insertEmpSQL = "INSERT INTO employee (name, gender, start_date, dept_id) VALUES (?, ?, ?, ?)";
        String insertContactSQL = "INSERT INTO contact (phone, email, address, employee_id) VALUES (?, ?, ?, ?)";
        String insertPayrollSQL = "INSERT INTO payroll (basic_pay, deductions, taxable_pay, income_tax, net_pay, salary, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbService.getInstance().getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            int employeeId;

            // Insert into employee table
            try (PreparedStatement empStmt = conn.prepareStatement(insertEmpSQL, Statement.RETURN_GENERATED_KEYS)) {
                empStmt.setString(1, employee.getFirst_name());
                empStmt.setString(2, employee.getGender());
                empStmt.setDate(3, employee.getStart_date());
                empStmt.setInt(4, employee.getDepartment_id());

                int rowsAffected = empStmt.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    throw new EmployeePayrollException("Failed to insert employee.");
                }

                try (ResultSet rs = empStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        employeeId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        throw new EmployeePayrollException("Failed to retrieve employee ID.");
                    }
                }
            }

            // Insert into contact table
            try (PreparedStatement contactStmt = conn.prepareStatement(insertContactSQL)) {
                contactStmt.setString(1, contact.getPhone_number());
                contactStmt.setString(2, contact.getEmail());
                contactStmt.setString(3, contact.getAddress());
                contactStmt.setInt(4, employeeId);
                contactStmt.executeUpdate();
            }

            // Insert into payroll table  according to UC 8
            double salary = payroll.getSalary();
            double deduction = salary * 0.2;
            double taxablePay = salary - deduction;
            double incomeTax = taxablePay * 0.1;
            double netPay = salary - incomeTax;
            double basicPay = salary;
            try (PreparedStatement payrollStmt = conn.prepareStatement(insertPayrollSQL)) {
                payrollStmt.setDouble(1, basicPay);
                payrollStmt.setDouble(2, deduction);
                payrollStmt.setDouble(3, taxablePay);
                payrollStmt.setDouble(4, incomeTax);
                payrollStmt.setDouble(5, netPay);
                payrollStmt.setDouble(6, salary);
                payrollStmt.setInt(7, employeeId);
                payrollStmt.executeUpdate();
            }

            conn.commit(); // All went well, commit it

        } catch (SQLException e) {
            throw new EmployeePayrollException("Error while inserting employee with details: " + e.getMessage());
        }
    }

    public static void deleteEmployee(int employeeId) throws EmployeePayrollException {
        String deleteEmpSQL = "DELETE FROM employee WHERE id = ?";

        try (Connection conn = DbService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteEmpSQL)) {

            stmt.setInt(1, employeeId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new EmployeePayrollException("No employee found with ID: " + employeeId);
            }

            System.out.println("Employee and related records deleted successfully.");

        } catch (SQLException e) {
            throw new EmployeePayrollException("Error deleting employee: " + e.getMessage());
        }
    }

    public static void removeEmployee(int employeeId) throws EmployeePayrollException {
        String query = "UPDATE employee SET is_active = FALSE WHERE id = ?";
        try (Connection conn = DbService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new EmployeePayrollException(e.getMessage());
        }
    }


}
