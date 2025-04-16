
package com.payroll;

import com.payroll.dtos.EmployeePayrollDTOS;
import com.payroll.services.DbService;
import com.payroll.services.PayrollServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PayrollServiceTest {
    @Test
    void compareEmployeeDtoWithDB(){
        String name = "Bob Smith";
        String getIdQuery = "SELECT id FROM employee WHERE name=?";
        String getSalaryQuery = "SELECT salary FROM payroll WHERE employee_id=?";


        try (Connection conn = DbService.getInstance().getConnection()){
            PreparedStatement idStmt = conn.prepareStatement(getIdQuery);
            idStmt.setString(1, name);
            ResultSet idRs = idStmt.executeQuery();

            if (!idRs.next()) {
                Assertions.fail("Employee with name '" + name + "' not found in DB.");
            }

            int employeeId = idRs.getInt("id");

            PreparedStatement salaryStmt = conn.prepareStatement(getSalaryQuery);
            salaryStmt.setInt(1, employeeId);
            ResultSet salaryRs = salaryStmt.executeQuery();

            if (!salaryRs.next()) {
                Assertions.fail("Payroll record for employee_id '" + employeeId + "' not found.");
            }

            double dbSalary = salaryRs.getDouble("salary");

            EmployeePayrollDTOS dto = PayrollServices.getEmployeePayroll(employeeId);

            Assertions.assertNotNull(dto, "DTO returned null from PayrollService");
            Assertions.assertEquals(dbSalary, dto.getPayroll().getSalary(), 0.01, "Salary mismatch");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
