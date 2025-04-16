package com.payroll.entities;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Employee {
    int id;
    String first_name;
    String gender;
    Date start_date;
    int department_id;

    public Employee(int employee_id, String first_name, String gender, Date start_date, int department_id) {
        this.id = employee_id;
        this.first_name = first_name;
        this.gender = gender;
        this.start_date = start_date;
        this.department_id = department_id;

    }

    @Override
    public String toString() {
        return "[ Employee ID: " + id + " Employee Name: " + first_name + "]";
    }
}
