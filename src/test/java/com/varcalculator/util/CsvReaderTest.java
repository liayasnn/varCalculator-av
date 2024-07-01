package com.varcalculator;

import com.varcalculator.model.Trade;
import com.varcalculator.util.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CsvReaderTest {

    private CsvReader csvReader;

    @BeforeEach
    public void setUp() {
        csvReader = new CsvReader();
    }

    @Test
    public void testReadTradesFromCsv() throws IOException {
        String content = "Date,Value,Returns,Trade\n01/01/2023,95000,-1.05%,AAPL\n";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", content.getBytes());

        List<Trade> trades = csvReader.readTrades(file);

        assertEquals(1, trades.size());
        assertEquals("AAPL", trades.get(0).getName());
        assertEquals(95000, trades.get(0).getValue());
        assertEquals(-0.0105, trades.get(0).getHistoricalReturns().get(0));
    }

    @Test
    public void testReadTradesFromCsvWithInvalidData() {
        String content = "Date,Value,Returns,Trade\nInvalid,Data,Here,AAPL\n";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", content.getBytes());

        assertThrows(IOException.class, () -> csvReader.readTrades(file));
    }


}
