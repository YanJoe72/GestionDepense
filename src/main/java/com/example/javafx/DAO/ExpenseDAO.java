package com.example.javafx.DAO;

import com.example.javafx.models.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseDAO {

    private static ObservableList<Expense> expensesList;

    public void setExpensesList(ObservableList<Expense> expensesList) {
        this.expensesList = expensesList;
    }

    public static boolean insertExpense(Expense expense) {
        String insertValue =
                "INSERT INTO expense (date, housing, food, goingOut, transportation, travel, tax, other) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(insertValue);

            statement.setString(1, expense.getPeriode());
            statement.setDouble(2, expense.getLogement());
            statement.setDouble(3, expense.getNourriture());
            statement.setDouble(4, expense.getSorties());
            statement.setDouble(5, expense.getVoiture());
            statement.setDouble(6, expense.getVoyage());
            statement.setDouble(7, expense.getImpots());
            statement.setDouble(8, expense.getAutres());

            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Expense> selectExpense() {
        ObservableList<Expense> expensesList = FXCollections.observableArrayList();
        String selectValue =
                "SELECT * FROM expense";


        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(selectValue);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String periode = resultSet.getString("date");
                double logement = resultSet.getDouble("housing");
                double nourriture = resultSet.getDouble("food");
                double sorties = resultSet.getDouble("goingOut");
                double voiture = resultSet.getDouble("transportation");
                double voyage = resultSet.getDouble("travel");
                double impots = resultSet.getDouble("tax");
                double autres = resultSet.getDouble("other");
                double total = logement + nourriture + sorties + voiture + voyage + impots + autres;

                Expense expense = new Expense(periode, total, logement, nourriture, sorties , voiture, voyage, impots, autres);
                expensesList.add(expense);
            }

            return expensesList;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
