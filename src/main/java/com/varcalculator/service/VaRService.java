package com.varcalculator.service;

import com.varcalculator.model.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaRService {

    public double calculateVaR(Trade trade, double confidenceLevel) {
        List<Double> sortedReturns = trade.getHistoricalReturns().stream()
                .sorted().toList();
        int index = (int) ((1 - confidenceLevel) * sortedReturns.size());
        return Math.abs(sortedReturns.get(index)) * trade.getValue();
    }

    public double calculateVaRForPortfolio(List<Trade> trades, double confidenceLevel) {
        double portfolioVaR = 0;
        for (Trade trade : trades) {
            portfolioVaR += calculateVaR(trade, confidenceLevel);
        }
        return portfolioVaR;
    }
}
