package com.varcalculator.model;

import java.util.List;

public class Trade {
    private String name;
    private double value;
    private List<Double> historicalReturns;

    public Trade(String name, double value, List<Double> historicalReturns) {
        this.name = name;
        this.value = value;
        this.historicalReturns = historicalReturns;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<Double> getHistoricalReturns() {
        return historicalReturns;
    }

    public void setHistoricalReturns(List<Double> historicalReturns) {
        this.historicalReturns = historicalReturns;
    }
}
