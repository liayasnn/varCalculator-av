package com.varcalculator.model;

import java.util.List;

public class Portfolio {
    private List<Trade> trades;

    public Portfolio(List<Trade> trades) {
        this.trades = trades;
    }

    // Getters and Setters

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }
}
