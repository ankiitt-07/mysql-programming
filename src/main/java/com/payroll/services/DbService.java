package com.payroll.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbService {

    public static final String dbUrl = System.getenv("DATABASE_URL");
    public static final String dbUser = System.getenv("DATABASE_USER");
    public static final String dbPass = System.getenv("DATABASE_PASSWORD");

    public static DbService instance;

    private DbService() {}

    public static synchronized DbService getInstance(){
        if(instance == null){
            instance = new DbService();
        }
        return instance;
    }


    public  Connection getConnection() throws SQLException {
         return DriverManager.getConnection(dbUrl, dbUser, dbPass);

    }
    public void isConnectionValid() {
        try (Connection conn = getConnection()) {
            boolean valid = conn != null && conn.isValid(1);
            System.out.println("Database connection was " + (valid ? "successful" : "unsuccessful"));
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());

        }
}
}
