package com.example.javafx;

import com.example.javafx.DAO.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.File;
import java.util.Objects;

public class HelloApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Démarrage de l'application");

        logger.debug("Vérification de la base de données");
        boolean dbStatus = Database.isOK();
        logger.debug("Statut de la base de données: {}", dbStatus);

        logger.debug("Chargement du fichier FXML");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/javafx/table-board-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1620, 1080);
        logger.debug("Fichier FXML chargé avec succès");

        try {
            logger.debug("Chargement du logo");
            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/javafx/assets/angular.png")));
            stage.getIcons().add(logo);
            logger.debug("Logo chargé avec succès");
        } catch (Exception e) {
            logger.error("Erreur lors du chargement du logo", e);
        }

        stage.setTitle("Gestion des Dépenses");
        stage.setScene(scene);
        logger.info("Affichage de l'interface utilisateur");
        stage.show();
    }

    public static String getLogDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        String logDir;

        if (os.contains("win")) {
            logDir = System.getenv("APPDATA") + "\\GestionDepence\\logs";
        } else if (os.contains("mac")) {
            logDir = System.getProperty("user.home") + "/Library/Application Support/GestionDepence/logs";
        } else if (os.contains("nix") || os.contains("nux")) {
            logDir = System.getProperty("user.home") + "/.local/share/GestionDepence/logs";
        } else {
            logDir = System.getProperty("user.home") + "/GestionDepence/logs";
        }

        File logFolder = new File(logDir);
        if (!logFolder.exists()) {
            logFolder.mkdirs();
        }

        return logDir;
    }

    public static void main(String[] args) {
        System.setProperty("log.dir", getLogDirectory());
        PropertyConfigurator.configure(HelloApplication.class.getResourceAsStream("/com/example/javafx/log4j.properties"));
        launch();
    }
}