package com.example.javafx.DAO;

import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Database.class);

    private static final String DB_NAME = "database.db";
    private static final String LOCATION = getDatabasePath();

    public static boolean isOK() {
        if (!checkDrivers()) {
            System.out.println("Drivers not available");
            return false;
        }

        if (!checkConnection()) {
            System.out.println("Connection not available");
            return false;
        }
        System.out.println(createTableIfNotExists());
        return createTableIfNotExists();
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            log.error("Could not start SQLite Drivers", classNotFoundException);
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

    private static boolean createTableIfNotExists() {
        String createExpenseTable =
                """
                CREATE TABLE IF NOT EXISTS expense (
                    periode TEXT NOT NULL,
                    housing REAL NOT NULL,
                    food REAL NOT NULL,
                    goingOut REAL NOT NULL,
                    transportation REAL NOT NULL,
                    travel REAL NOT NULL,
                    tax REAL NOT NULL,
                    other REAL NOT NULL
                );
                """;
        String CreateIncomeTable = """
                CREATE TABLE IF NOT EXISTS income (
                    period TEXT NOT NULL,
                    salary REAL NOT NULL,
                    grants REAL NOT NULL,
                    selfBusiness REAL NOT NULL,
                    passiveIncome REAL NOT NULL,
                    other REAL NOT NULL
                );
                """;

        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createExpenseTable);

            statement.executeUpdate(CreateIncomeTable);

            return true;
        } catch (SQLException exception) {
            return false;
        }
    }


    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + LOCATION);
        } catch (SQLException exception) {
            log.error("Could not connect to SQLite DB at " + LOCATION, exception);
            return null;
        }
        return connection;
    }



    private static String getDatabasePath() {
        String appData;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            appData = System.getenv("APPDATA");
            if (appData == null) {
                appData = System.getProperty("user.home") + "\\AppData\\Roaming";
            }
        }
        else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            appData = System.getProperty("user.home") + "/Library/Application Support";
        }
        else if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux")) {
            appData = System.getProperty("user.home") + "/.local/share";
        }
        else {
            appData = System.getProperty("user.home");
        }

        String appFolder = appData + File.separator + "GestionDepence";

        File folder = new File(appFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return appFolder + File.separator + DB_NAME;
    }


}
