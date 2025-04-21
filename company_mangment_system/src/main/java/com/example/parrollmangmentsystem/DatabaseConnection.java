package com.example.parrollmangmentsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/company system";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1442009";

    public static Connection connect() {
        Connection conn =null ;
        try {
            conn  = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

}
