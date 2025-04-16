package com.payroll.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayrollAnalysisDTO {
    private String gender;
    private double totalSalary;
    private double avgSalary;
    private double minSalary;
    private double maxSalary;
    private int employeeCount;

    public PayrollAnalysisDTO(String gender, double totalSalary, double avgSalary, double minSalary, double maxSalary, int employeeCount) {
        this.gender = gender;
        this.totalSalary = totalSalary;
        this.avgSalary = avgSalary;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.employeeCount = employeeCount;
    }

    @Override
    public String toString() {
        return "[ Gender: " + gender + ", Total Salary: " + totalSalary + ", Avg Salary: " + avgSalary + ", Min Salary: " + minSalary + ", Max Salary: " + maxSalary + ", Employee Count: " + employeeCount + " ]";
    }
}