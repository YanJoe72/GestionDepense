package com.example.javafx.controllers;

import com.example.javafx.DAO.IncomeDAO;
import com.example.javafx.models.Income;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IncomeFormDialogController {

    @FXML private TextField period;
    @FXML private TextField salary;
    @FXML private TextField grants;
    @FXML private TextField selfBusiness;
    @FXML private TextField passiveIncome;
    @FXML private TextField other;
    @FXML private Button submitButton;

    private Stage dialogStage;
    private boolean isSubmitted = false;
    private ObservableList<Income> incomeList;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setIncomeList(ObservableList<Income> incomeList) {
        this.incomeList = incomeList;
    }

    @FXML
    private void initialize() {
        submitButton.setDisable(true);

        period.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        salary.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        grants.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        selfBusiness.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        passiveIncome.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        other.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
    }

    private void validateForm() {
        boolean isValid = !period.getText().isEmpty() &&
                isNumeric(salary.getText()) &&
                isNumeric(grants.getText()) &&
                isNumeric(selfBusiness.getText()) &&
                isNumeric(passiveIncome.getText()) &&
                isNumeric(other.getText());

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
            String periodValue = period.getText();
            double salaryValue = Double.parseDouble(salary.getText());
            double grantsValue = Double.parseDouble(grants.getText());
            double selfBusinessValue = Double.parseDouble(selfBusiness.getText());
            double passiveIncomeValue = Double.parseDouble(passiveIncome.getText());
            double otherValue = Double.parseDouble(other.getText());
            double total = salaryValue + grantsValue + selfBusinessValue + passiveIncomeValue + otherValue;

            Income income = new Income(periodValue, total, salaryValue, grantsValue, selfBusinessValue, passiveIncomeValue, otherValue);
            incomeList.add(income);
            IncomeDAO.insertIncome(income);
            isSubmitted = true;
            dialogStage.close();
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez entrer des nombres valides pour les revenus.");
        }
    }
}