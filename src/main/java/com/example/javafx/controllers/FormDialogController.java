package com.example.javafx.controllers;

import com.example.javafx.DAO.Database;
import com.example.javafx.DAO.ExpenseDAO;
import com.example.javafx.models.Expense;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormDialogController {

    @FXML private TextField periode;
    @FXML private TextField logement;
    @FXML private TextField nourriture;
    @FXML private TextField sorties;
    @FXML private TextField transport;
    @FXML private TextField voyage;
    @FXML private TextField impots;
    @FXML private TextField autres;
    @FXML private Button submitButton;  // Le bouton à désactiver/activer

    private Stage dialogStage;
    private boolean isSubmitted = false;
    private ObservableList<Expense> expensesList;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setExpensesList(ObservableList<Expense> expensesList) {
        this.expensesList = expensesList;
    }

    @FXML
    private void initialize() {
        submitButton.setDisable(true);

        periode.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        logement.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        nourriture.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        sorties.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        transport.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        voyage.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        impots.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        autres.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        boolean isValid = !periode.getText().isEmpty() &&
                !logement.getText().isEmpty() && isNumeric(logement.getText()) &&
                !nourriture.getText().isEmpty() && isNumeric(nourriture.getText()) &&
                !sorties.getText().isEmpty() && isNumeric(sorties.getText()) &&
                !transport.getText().isEmpty() && isNumeric(transport.getText()) &&
                !voyage.getText().isEmpty() && isNumeric(voyage.getText()) &&
                !impots.getText().isEmpty() && isNumeric(impots.getText()) &&
                !autres.getText().isEmpty() && isNumeric(autres.getText());

        submitButton.setDisable(!isValid);
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleSubmit() {
        try {
            String periodeText = periode.getText();
            double logementValue = Double.parseDouble(logement.getText());
            double nourritureValue = Double.parseDouble(nourriture.getText());
            double sortiesValue = Double.parseDouble(sorties.getText());
            double transportValue = Double.parseDouble(transport.getText());
            double voyageValue = Double.parseDouble(voyage.getText());
            double impotsValue = Double.parseDouble(impots.getText());
            double autresValue = Double.parseDouble(autres.getText());
            double total = logementValue + sortiesValue + transportValue + voyageValue + impotsValue + autresValue;

            Expense expense = new Expense(periodeText, total, logementValue, nourritureValue, sortiesValue, transportValue, voyageValue, impotsValue, autresValue);
            expensesList.add(expense);
            ExpenseDAO.insertExpense(expense);
            isSubmitted = true;
            dialogStage.close();
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez entrer des nombres valides pour les dépenses.");
        }
    }
}
