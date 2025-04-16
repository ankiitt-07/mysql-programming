package com.payroll.dtos;

import com.payroll.entities.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmployeePayrollDTOS {
    Contacts contacts;
    Department department;
    Employee employee;
    Payroll payroll;

    public EmployeePayrollDTOS(Contacts contacts, Department department, Employee employee, Payroll payroll) {
        this.contacts = contacts;
        this.department = department;
        this.employee = employee;
        this.payroll = payroll;
    }

    @Override
    public String toString() {
        return "EmployeePayrollDto [employee=" + employee + ", payroll=" + payroll + ", contacts=" + contacts + ", department=" + department + "]";
    }
}
