package com.bank.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dblink {
    public static Connection connect() throws SQLException {

        try {
            // Get database credentials from DatabaseConfig class
            String jdbcUrl = DBConfig.getDbUrl();
            String user = DBConfig.getDbUsername();
            String password = DBConfig.getDbPassword();

            // Open a connection
            return DriverManager.getConnection(jdbcUrl, user, password);

        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
