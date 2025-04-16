package com.payroll.mapping;

import com.payroll.dtos.PayrollAnalysisDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ToPayrollAnalysisDto {
    public static PayrollAnalysisDTO map (ResultSet rs) throws SQLException {
        return new PayrollAnalysisDTO(
                rs.getString("gender"),
                rs.getDouble("total_salary"),
                rs.getDouble("average_salary"),
                rs.getDouble("min_salary"),
                rs.getDouble("max_salary"),
                rs.getInt("employee_count")
        );
    }
}