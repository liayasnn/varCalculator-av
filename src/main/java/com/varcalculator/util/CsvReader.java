package com.varcalculator.util;

import com.varcalculator.model.Trade;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvReader {
    private static final String[] HEADERS = {"Date", "Value", "Returns", "Trade"};

    public List<Trade> readTrades(MultipartFile file) throws IOException {
        Map<String, List<Double>> returnsMap = new HashMap<>();
        Map<String, Double> valuesMap = new HashMap<>();

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(HEADERS).withSkipHeaderRecord(true))) {

            for (CSVRecord csvRecord : csvParser) {
                try {
                    String tradeName = csvRecord.get("Trade");
                    double value = Double.parseDouble(csvRecord.get("Value").replace(",", ""));
                    String returnStr = csvRecord.get("Returns").replace("%", "").trim();
                    double returnVal = Double.parseDouble(returnStr) / 100.0;

                    returnsMap.computeIfAbsent(tradeName, k -> new ArrayList<>()).add(returnVal);
                    valuesMap.put(tradeName, value);
                } catch (IllegalArgumentException e) {
                    // Log the error and throw an IOException if format is invalid
                    System.err.println("Error parsing record: " + csvRecord);
                    e.printStackTrace();
                    throw new IOException("Invalid data format", e);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        List<Trade> trades = new ArrayList<>();
        for (Map.Entry<String, List<Double>> entry : returnsMap.entrySet()) {
            String tradeName = entry.getKey();
            double tradeValue = valuesMap.get(tradeName);
            trades.add(new Trade(tradeName, tradeValue, entry.getValue()));
        }
        return trades;
    }
}
