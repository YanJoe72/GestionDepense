package com.example.javafx.models;

public class Expense {

    private String periode;
    private double total;
    private double logement;
    private double nourriture;
    private double sorties;
    private double voiture;
    private double voyage;
    private double impots;
    private double autres;

    public Expense() {}

    public Expense(String periode, double total, double logement, double nourriture, double sorties, double voiture, double voyage, double impots, double autres) {
        this.periode = periode;
        this.total = total;
        this.logement = logement;
        this.nourriture = nourriture;
        this.sorties = sorties;
        this.voiture = voiture;
        this.voyage = voyage;
        this.impots = impots;
        this.autres = autres;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getLogement() {
        return logement;
    }

    public void setLogement(double logement) {
        this.logement = logement;
    }

    public double getNourriture() {
        return nourriture;
    }

    public void setNourriture(double nourriture) {
        this.nourriture = nourriture;
    }

    public double getSorties() {
        return sorties;
    }

    public void setSorties(double sorties) {
        this.sorties = sorties;
    }

    public double getVoiture() {
        return voiture;
    }

    public void setVoiture(double voiture) {
        this.voiture = voiture;
    }

    public double getVoyage() {
        return voyage;
    }

    public void setVoyage(double voyage) {
        this.voyage = voyage;
    }

    public double getImpots() {
        return impots;
    }

    public void setImpots(double impots) {
        this.impots = impots;
    }

    public double getAutres() {
        return autres;
    }

    public void setAutres(double autres) {
        this.autres = autres;
    }
}
