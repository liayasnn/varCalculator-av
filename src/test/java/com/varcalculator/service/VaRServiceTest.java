package com.varcalculator.service;

import com.varcalculator.model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VaRServiceTest {

    private VaRService varService;

    @BeforeEach
    public void setUp() {
        varService = new VaRService();
    }

    @Test
    public void testCalculateVaRForSingleTrade() {
        Trade trade = new Trade("AAPL", 100000, Arrays.asList(0.01, -0.02, 0.03));
        double confidenceLevel = 0.95;

        // For a confidence level of 95%, the VaR should be calculated based on the worst 5% of returns.
        double expectedVaR = Math.abs(-0.02) * 100000;  // -0.02 is the worst return in the sorted list

        double result = varService.calculateVaR(trade, confidenceLevel);

        assertEquals(expectedVaR, result, 0.0001);
    }

    @Test
    public void testCalculateVaRForPortfolio() {
        List<Trade> trades = Arrays.asList(
                new Trade("AAPL", 100000, Arrays.asList(0.01, -0.02, 0.03)),
                new Trade("GOOGL", 150000, Arrays.asList(0.02, -0.01, 0.01))
        );
        double confidenceLevel = 0.95;

        // Calculate individual VaRs
        double expectedVaR_AAPL = Math.abs(-0.02) * 100000;
        double expectedVaR_GOOGL = Math.abs(-0.01) * 150000;

        // Sum of individual VaRs for the portfolio VaR
        double expectedPortfolioVaR = expectedVaR_AAPL + expectedVaR_GOOGL;

        double result = varService.calculateVaRForPortfolio(trades, confidenceLevel);

        assertEquals(expectedPortfolioVaR, result, 0.0001);
    }
}
