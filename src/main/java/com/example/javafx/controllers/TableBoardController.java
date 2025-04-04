package com.example.javafx.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import com.example.javafx.models.Expense;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.example.javafx.DAO.ExpenseDAO.selectLast12Expenses;

public class TableBoardController {

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private ChoiceBox<String> choiceBox;

    private ObservableList<Expense> expenses;

    public void initialize() {
        expenses = selectLast12Expenses();

        if (expenses != null && !expenses.isEmpty()) {
            loadPieChart(expenses);
            loadLineChart(expenses);
            loadChoiceBox(expenses);
            loadBarChart(expenses);
        }
    }

    private void loadBarChart(ObservableList<Expense> expenses) {
        barChart.getData().clear();

        XYChart.Series<String, Number> revenuesSeries = new XYChart.Series<>();
        revenuesSeries.setName("Revenus");

        XYChart.Series<String, Number> expensesSeries = new XYChart.Series<>();
        expensesSeries.setName("Dépenses");

        Map<String, Double> revenusMap = new HashMap<>();
        Map<String, Double> depensesMap = new HashMap<>();
        Set<String> allPeriods = new TreeSet<>();

        for (Expense expense : expenses) {
            String periode = expense.getPeriode();
            allPeriods.add(periode);

            depensesMap.put(periode, expense.getTotal());
            revenusMap.put(periode, expense.getTotal());
        }

        for (String periode : allPeriods) {
            double depenses = depensesMap.getOrDefault(periode, 0.0);
            double revenus = revenusMap.getOrDefault(periode, 0.0);

            expensesSeries.getData().add(new XYChart.Data<>(periode, depenses));
            revenuesSeries.getData().add(new XYChart.Data<>(periode, revenus));
        }

        barChart.getData().addAll(revenuesSeries, expensesSeries);
    }

    private void loadPieChart(ObservableList<Expense> expenses) {
        double totalHousing = 0, totalFood = 0, totalGoingOut = 0, totalTransportation = 0;
        double totalTravel = 0, totalTax = 0, totalOther = 0;

        for (Expense expense : expenses) {
            totalHousing += expense.getLogement();
            totalFood += expense.getNourriture();
            totalGoingOut += expense.getSorties();
            totalTransportation += expense.getVoiture();
            totalTravel += expense.getVoyage();
            totalTax += expense.getImpots();
            totalOther += expense.getAutres();
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Logement", totalHousing),
                new PieChart.Data("Nourriture", totalFood),
                new PieChart.Data("Sorties", totalGoingOut),
                new PieChart.Data("Transport", totalTransportation),
                new PieChart.Data("Voyage", totalTravel),
                new PieChart.Data("Impôts", totalTax),
                new PieChart.Data("Autres", totalOther)
        );

        pieChart.setData(pieChartData);
    }

    private void loadLineChart(ObservableList<Expense> expenses) {
        lineChart.getData().clear();

        XYChart.Series<String, Number> logementSeries = new XYChart.Series<>();
        logementSeries.setName("Logement");

        XYChart.Series<String, Number> nourritureSeries = new XYChart.Series<>();
        nourritureSeries.setName("Nourriture");

        XYChart.Series<String, Number> sortiesSeries = new XYChart.Series<>();
        sortiesSeries.setName("Sorties");

        XYChart.Series<String, Number> voitureSeries = new XYChart.Series<>();
        voitureSeries.setName("Transport");

        XYChart.Series<String, Number> voyageSeries = new XYChart.Series<>();
        voyageSeries.setName("Voyage");

        XYChart.Series<String, Number> impotsSeries = new XYChart.Series<>();
        impotsSeries.setName("Impôts");

        XYChart.Series<String, Number> autresSeries = new XYChart.Series<>();
        autresSeries.setName("Autres");

        for (Expense expense : expenses) {
            String periode = expense.getPeriode();
            logementSeries.getData().add(new XYChart.Data<>(periode, expense.getLogement()));
            nourritureSeries.getData().add(new XYChart.Data<>(periode, expense.getNourriture()));
            sortiesSeries.getData().add(new XYChart.Data<>(periode, expense.getSorties()));
            voitureSeries.getData().add(new XYChart.Data<>(periode, expense.getVoiture()));
            voyageSeries.getData().add(new XYChart.Data<>(periode, expense.getVoyage()));
            impotsSeries.getData().add(new XYChart.Data<>(periode, expense.getImpots()));
            autresSeries.getData().add(new XYChart.Data<>(periode, expense.getAutres()));
        }

        lineChart.getData().addAll(
                logementSeries,
                nourritureSeries,
                sortiesSeries,
                voitureSeries,
                voyageSeries,
                impotsSeries,
                autresSeries
        );
    }

    private void loadChoiceBox(ObservableList<Expense> expenses) {
        ObservableList<String> periods = FXCollections.observableArrayList();

        for (Expense expense : expenses) {
            String period = expense.getPeriode();
            if (!periods.contains(period)) {
                periods.add(period);
            }
        }

        choiceBox.setItems(periods);

        if (!periods.isEmpty()) {
            choiceBox.getSelectionModel().select(0);
        }

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ObservableList<Expense> filteredExpenses = FXCollections.observableArrayList();
                for (Expense expense : expenses) {
                    if (expense.getPeriode().equals(newValue)) {
                        filteredExpenses.add(expense);
                    }
                }
                loadPieChart(filteredExpenses);
            }
        });
    }
}