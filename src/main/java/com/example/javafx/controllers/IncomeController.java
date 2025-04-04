package com.example.javafx.controllers;

import com.example.javafx.DAO.IncomeDAO;
import com.example.javafx.models.Income;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class IncomeController {

    @FXML
    private TableView<Income> tableView;
    private ObservableList<Income> incomesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (tableView != null) {
            System.out.println("TableView initialisée avec succès");
            incomesList.addAll(IncomeDAO.selectAllIncomes());
            tableView.setItems(incomesList);
        } else {
            System.out.println("Erreur : TableView non initialisée.");
        }
    }

    public void showFormDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/income-form-view.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Formulaire");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));

            IncomeFormDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIncomeList(incomesList);

            dialogStage.showAndWait();
            if (controller.isSubmitted()) {
                tableView.refresh();
            }


            if (controller.isSubmitted()) {
                System.out.println("Formulaire envoyé !");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}