package com.payroll.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department {
     int dept_id;
     String dept_name;

    public Department(int dept_id, String dept_name) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
    }

    @Override
    public String toString() {
        return "[dept_id=" + dept_id + ", dept_name=" + dept_name + "]";
    }
}
