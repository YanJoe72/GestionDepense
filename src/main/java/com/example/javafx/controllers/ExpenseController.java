package com.example.javafx.controllers;

import com.example.javafx.DAO.ExpenseDAO;
import com.example.javafx.models.Expense;
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

import static com.example.javafx.DAO.ExpenseDAO.selectExpense;

public class ExpenseController {

    @FXML
    private TableView<Expense> tableView;
    private ObservableList<Expense> expensesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        expensesList.addAll(ExpenseDAO.selectExpense());
        tableView.setItems(expensesList);
    }

    public void showFormDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/form-view.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Formulaire");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));

            FormDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setExpensesList(expensesList);

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
