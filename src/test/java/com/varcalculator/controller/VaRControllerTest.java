package com.varcalculator.controller;

import com.varcalculator.controller.VaRController;
import com.varcalculator.model.Trade;
import com.varcalculator.service.VaRService;
import com.varcalculator.util.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class VaRControllerTest {

    @Mock
    private VaRService varService;

    @Mock
    private CsvReader csvReader;

    @InjectMocks
    private VaRController varController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateVaRForTrade() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "trade.csv", "text/csv", "content".getBytes());
        List<Trade> trades = Arrays.asList(new Trade("AAPL", 100000, Arrays.asList(0.01, -0.02, 0.03)));

        when(csvReader.readTrades(file)).thenReturn(trades);
        when(varService.calculateVaR(trades.get(0), 0.95)).thenReturn(500.0);

        double result = varController.calculateVaRForTrade(file, 0.95);

        assertEquals(500.0, result);
    }

    @Test
    public void testCalculateVaRForPortfolio() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "portfolio.csv", "text/csv", "content".getBytes());
        List<Trade> trades = Arrays.asList(
                new Trade("AAPL", 100000, Arrays.asList(0.01, -0.02, 0.03)),
                new Trade("GOOGL", 150000, Arrays.asList(0.02, -0.01, 0.01))
        );

        when(csvReader.readTrades(file)).thenReturn(trades);
        when(varService.calculateVaRForPortfolio(trades, 0.95)).thenReturn(1000.0);

        double result = varController.calculateVaRForPortfolio(file, 0.95);

        assertEquals(1000.0, result);
    }
}
