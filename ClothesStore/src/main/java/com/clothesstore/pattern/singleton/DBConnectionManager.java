package com.clothesstore.pattern.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

    private static DBConnectionManager instance;

    private static final String URL = "jdbc:mysql://localhost:3306/clothes_store?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private DBConnectionManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    public static DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}