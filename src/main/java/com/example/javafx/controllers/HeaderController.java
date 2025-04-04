package com.example.javafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

public class HeaderController {

    @FXML
    private MenuBar root;

    private void switchScene(String fxmlFile) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/" + fxmlFile));
        Parent newRoot = loader.load();
        Scene scene = new Scene(newRoot, 1620, 1080);
        stage.setScene(scene);
    }

    @FXML
    private void goToTableBoardView() throws IOException {
        switchScene("table-board-view.fxml");
    }

    @FXML
    private void goToExpenseView() throws IOException {
        switchScene("expense-view.fxml");
    }

    @FXML
    private void goToIncomeView() throws IOException {
        switchScene("income-view.fxml");
    }
}
