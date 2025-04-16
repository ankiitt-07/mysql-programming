package com.payroll.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payroll {
    int payroll_id;
    double basic_pay;
    double deduction;
    double taxable_pay;
    double income_tax;
    double net_pay;
    double salary;
    int employee_id;

    public Payroll(int payroll_id, double basic_pay, double deduction, double taxable_pay, double income_tax, double net_pay, double salary, int employee_id) {
        this.payroll_id = payroll_id;
        this.basic_pay = basic_pay;
        this.deduction = deduction;
        this.taxable_pay = taxable_pay;
        this.income_tax = income_tax;
        this.net_pay = net_pay;
        this.salary = salary;
        this.employee_id = employee_id;
    }


    @Override
    public String toString() {
        return "[ Payroll id: " + payroll_id + " Salary: " + salary+"]";
    }
}
