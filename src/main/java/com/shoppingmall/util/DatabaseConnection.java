package com.shoppingmall.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseConnection {
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    private static String DB_DRIVER;

    static {
        loadDatabaseProperties();
    }

    private static void loadDatabaseProperties() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Unable to find db.properties, using defaults");
                DB_URL = "jdbc:sqlite:shoppingmall.db";
                DB_DRIVER = "org.sqlite.JDBC";
                return;
            }
            properties.load(input);
            DB_URL = properties.getProperty("db.url");
            DB_USERNAME = properties.getProperty("db.username");
            DB_PASSWORD = properties.getProperty("db.password");
            DB_DRIVER = properties.getProperty("db.driver");
        } catch (IOException ex) {
            System.err.println("Error loading database properties: " + ex.getMessage());
            DB_URL = "jdbc:sqlite:shoppingmall.db";
            DB_DRIVER = "org.sqlite.JDBC";
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + DB_DRIVER, e);
        }

        if (DB_USERNAME != null && DB_PASSWORD != null) {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } else {
            return DriverManager.getConnection(DB_URL);
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            String schemaPath = "src/main/resources/schema.sql";
            if (Files.exists(Paths.get(schemaPath))) {
                String schema = new String(Files.readAllBytes(Paths.get(schemaPath)));
                String[] statements = schema.split(";");
                
                try (Statement stmt = conn.createStatement()) {
                    for (String sql : statements) {
                        sql = sql.trim();
                        if (!sql.isEmpty() && !sql.startsWith("--")) {
                            stmt.execute(sql);
                        }
                    }
                }
                System.out.println("Database initialized successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading schema file: " + e.getMessage());
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
