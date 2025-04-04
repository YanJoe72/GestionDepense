package com.example.javafx.models;

public class Income {

    private String period;
    private double total;
    private double salary;
    private double grants;
    private double selfBusiness;
    private double passiveIncome;
    private double other;

    public Income(String period, double total, double salary, double grants, double selfBusiness, double passiveIncome, double other) {
        this.period = period;
        this.total = total;
        this.salary = salary;
        this.grants = grants;
        this.selfBusiness = selfBusiness;
        this.passiveIncome = passiveIncome;
        this.other = other;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getGrants() {
        return grants;
    }

    public void setGrants(double grants) {
        this.grants = grants;
    }

    public double getSelfBusiness() {
        return selfBusiness;
    }

    public void setSelfBusiness(double selfBusiness) {
        this.selfBusiness = selfBusiness;
    }

    public double getPassiveIncome() {
        return passiveIncome;
    }

    public void setPassiveIncome(double passiveIncome) {
        this.passiveIncome = passiveIncome;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }
}
