package com.example.javafx;

import com.example.javafx.DAO.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Database.isOK();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("expense-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 440);

        Image logo = new Image(getClass().getResourceAsStream("assets/angular.png"));
        stage.getIcons().add(logo);

        stage.setTitle("Gestion des Dépenses");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
