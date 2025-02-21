package com.example.javafx.DAO;

import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Database.class);

    /**
     * Location of database on macOS in ~/Library/Application Support/GestionDepense
     */
    private static final String DB_NAME = "database.db";
    private static final String LOCATION = getDatabasePath(); // Dynamically build the path

    public static boolean isOK() {
        if (!checkDrivers()) {
            System.out.println("Drivers not available");
            return false;
        }  //driver errors

        if (!checkConnection()) {
            System.out.println("Connection not available");
            return false;
        } //can't connect to db
        System.out.println(createTableIfNotExists());
        return createTableIfNotExists(); //tables didn't exist
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
        String createTables =
                """
                         CREATE TABLE IF NOT EXISTS expense(
                                  date TEXT NOT NULL,
                                  housing REAL NOT NULL,
                                  food REAL NOT NULL,
                                  goingOut REAL NOT NULL,
                                  transportation REAL NOT NULL,
                                  travel REAL NOT NULL,
                                  tax REAL NOT NULL,
                                  other REAL NOT NULL
                              );
                        """;
        System.out.println("Exécution de la requête : " + createTables);

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(createTables);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            log.error("Could not create tables in database", exception);
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

    /**
     * Construit le chemin complet de la base de données dans ~/Library/Application Support.
     */
    /*
    private static String getDatabasePath() {
        String libraryAppSupport = System.getProperty("user.home") + "/Library/Application Support";

        String appFolder = libraryAppSupport + File.separator + "GestionDepense";

        File folder = new File(appFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Renvoie le chemin complet vers la base de données
        return appFolder + File.separator + DB_NAME;
    }
     */

    private static String getDatabasePath() {
        String appData;

        // Vérifie si le système d'exploitation est Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // Sur Windows, on utilise %APPDATA% (AppData\Roaming)
            appData = System.getenv("APPDATA");
            if (appData == null) {
                appData = System.getProperty("user.home") + "\\AppData\\Roaming";
            }
        } else {
            // Sur macOS, on utilise ~/Library/Application Support
            appData = System.getProperty("user.home") + "/Library/Application Support";
        }

        // Chemin du dossier de l'application
        String appFolder = appData + File.separator + "GestionDepence";

        // Crée le dossier s'il n'existe pas déjà
        File folder = new File(appFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Renvoie le chemin complet vers la base de données
        return appFolder + File.separator + DB_NAME;
    }

}
