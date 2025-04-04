package com.example.javafx.DAO;

import com.example.javafx.models.Income;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class IncomeDAO {

    public static boolean insertIncome(Income income) {
        String insertQuery = "INSERT INTO income (period, salary, grants, selfBusiness, passiveIncome, other) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setString(1, income.getPeriod());
            statement.setDouble(2, income.getSalary());
            statement.setDouble(3, income.getGrants());
            statement.setDouble(4, income.getSelfBusiness());
            statement.setDouble(5, income.getPassiveIncome());
            statement.setDouble(6, income.getOther());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Income> selectAllIncomes() {
        ObservableList<Income> incomeList = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM income";

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String period = resultSet.getString("period");
                double salary = resultSet.getDouble("salary");
                double grants = resultSet.getDouble("grants");
                double selfBusiness = resultSet.getDouble("selfBusiness");
                double passiveIncome = resultSet.getDouble("passiveIncome");
                double other = resultSet.getDouble("other");

                double total = salary + grants + selfBusiness + passiveIncome + other;

                Income income = new Income(period, total, salary, grants, selfBusiness, passiveIncome, other);
                incomeList.add(income);
            }

            return incomeList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Income> selectIncomesLastMonth() {
        ObservableList<Income> incomeList = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM income WHERE date >= DATE('now', '-1 month')";

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String period = resultSet.getString("period");
                double salary = resultSet.getDouble("salary");
                double grants = resultSet.getDouble("grants");
                double selfBusiness = resultSet.getDouble("selfBusiness");
                double passiveIncome = resultSet.getDouble("passiveIncome");
                double other = resultSet.getDouble("other");

                double total = salary + grants + selfBusiness + passiveIncome + other;

                Income income = new Income(period, total, salary, grants, selfBusiness, passiveIncome, other);
                incomeList.add(income);
            }

            return incomeList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Income> selectLast12Incomes() {
        ObservableList<Income> incomeList = FXCollections.observableArrayList();
        String query = "SELECT * FROM income ORDER BY rowid DESC LIMIT 12";

        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String period = resultSet.getString("period");
                double salary = resultSet.getDouble("salary");
                double grants = resultSet.getDouble("grants");
                double selfBusiness = resultSet.getDouble("selfBusiness");
                double passiveIncome = resultSet.getDouble("passiveIncome");
                double other = resultSet.getDouble("other");

                double total = salary + grants + selfBusiness + passiveIncome + other;

                Income income = new Income(period, total, salary, grants, selfBusiness, passiveIncome, other);
                incomeList.add(income);
            }

            return incomeList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}