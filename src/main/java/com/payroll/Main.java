package com.payroll;


import com.payroll.Exceptions.EmployeePayrollException;
import com.payroll.dtos.EmployeePayrollDTOS;
import com.payroll.dtos.PayrollAnalysisDTO;
import com.payroll.services.DbService;
import com.payroll.services.PayrollServices;
import com.payroll.entities.*;

import java.sql.Date;
import java.util.List;

public class Main {
    static List<EmployeePayrollDTOS>employeePayroll;
    public static void main(String[] args) throws EmployeePayrollException {

        // UC 1-2
        DbService.getInstance().isConnectionValid();
        employeePayroll = PayrollServices.getEmployeePayrolls();

        for (EmployeePayrollDTOS employeePayrollDto : employeePayroll) {
            System.out.println(employeePayrollDto);
        }

        //UC 3-4
        System.out.println(PayrollServices.getEmployeePayroll(2));
        PayrollServices.updateEmployeeSalary("Bob Smith" , 5000.0);

        // UC 5
        List<EmployeePayrollDTOS> employees = PayrollServices.getEmployeesByDateRange(Date.valueOf("2021-05-03"), Date.valueOf("2024-02-01"));
        for (EmployeePayrollDTOS employee : employees) {
            System.out.println(employee);
        }

        // UC 6
        List<PayrollAnalysisDTO> payrollAnalysis = PayrollServices.getPayrollAnalysisByGender();
        for (PayrollAnalysisDTO payrollAnalysisDto : payrollAnalysis) {
            System.out.println(payrollAnalysisDto);
        }


        // UC 7
        Employee sahil = new Employee(0, "Sahil", "M", Date.valueOf("2023-03-15"), 1);
        Contacts sahilContact = new Contacts(0, "9876543210", "sahil.johnson@example.com", "123 Main St, New York", 0);
        Payroll sahilPayroll = new Payroll(0, 5000.00, 300.00, 4700.00, 500.00, 4200.00, 5200.00, 0);
        PayrollServices.addEmployeeWithDetails(sahil, sahilContact, sahilPayroll);

        //UC 8
        PayrollServices.deleteEmployee(2);


        //UC 9
        PayrollServices.removeEmployee(3);
    }
}