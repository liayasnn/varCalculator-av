package com.varcalculator.controller;

import com.varcalculator.model.Trade;
import com.varcalculator.service.VaRService;
import com.varcalculator.util.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/var")
public class VaRController {

    @Autowired
    private VaRService varService;

    @Autowired
    private CsvReader csvReader;

    @PostMapping("/trade")
    public double calculateVaRForTrade(@RequestParam("file") MultipartFile file, @RequestParam double confidenceLevel) throws IOException {
        List<Trade> trades = csvReader.readTrades(file);

        // Check only 1 trade in CSV
        Set<String> tradeNames = trades.stream().map(Trade::getName).collect(Collectors.toSet());
        if (tradeNames.size() != 1) {
            throw new IllegalArgumentException("CSV file contains more than 1 trade. It can only contain 1 type of trade.");
        }

        Trade trade = trades.get(0);
        double var = varService.calculateVaR(trade, confidenceLevel);
        return var;
    }

    @PostMapping("/portfolio")
    public double calculateVaRForPortfolio(@RequestParam("file") MultipartFile file, @RequestParam double confidenceLevel) throws IOException {
        List<Trade> trades = csvReader.readTrades(file);
        double var = varService.calculateVaRForPortfolio(trades, confidenceLevel);
        return var;
    }
}
