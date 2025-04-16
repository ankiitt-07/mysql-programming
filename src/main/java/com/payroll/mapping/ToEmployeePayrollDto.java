package com.payroll.mapping;

import com.payroll.dtos.EmployeePayrollDTOS;
import com.payroll.entities.Contacts;
import com.payroll.entities.Department;
import com.payroll.entities.Employee;
import com.payroll.entities.Payroll;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ToEmployeePayrollDto {
    public static EmployeePayrollDTOS map (ResultSet rs) throws SQLException {
        Department department = new Department(rs.getInt("dept_id"), rs.getString("dept_name"));
        Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("gender"), rs.getDate("start_date"), department.getDept_id());
        Contacts contact = new Contacts(rs.getInt("contact_id"), rs.getString("phone"), rs.getString("email"), rs.getString("address"), employee.getId());
        Payroll payroll = new Payroll(rs.getInt("payroll_id"),
                rs.getDouble("basic_pay"),
                rs.getDouble("deductions"),
                rs.getDouble("taxable_pay"),
                rs.getDouble("income_tax"),
                rs.getDouble("net_pay"),
                rs.getDouble("salary"),
                employee.getId());

        return new EmployeePayrollDTOS(contact,department,employee,payroll);
    }
}
