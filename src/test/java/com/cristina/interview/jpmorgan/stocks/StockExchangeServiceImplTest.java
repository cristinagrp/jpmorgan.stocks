package com.cristina.interview.jpmorgan.stocks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.cristina.interview.jpmorgan.stocks.Transaction.TransactionType;

/**
 * Tests {@link StockExchangeServiceImpl}. (Actually a mix of behavioural and unit testing, due to time restrictions.
 * Normally each class in the project would have its own test class with unit tests.)
 *
 * @author CristinaGroapa
 */
public class StockExchangeServiceImplTest {

    /** The object to test */
    private StockExchangeServiceImpl service;

    /**
     * POJO containing expected calculation results for a stock
     *
     * @author CristinaGroapa
     */
    private class ExpectedStockResult {
        Double dividendYield = Double.NaN;

        Double peRatio = Double.NaN;

        Double stockPrice = Double.NaN;
    }

    /** Expected calculation results for the test stocks */
    private Map<StockSymbol, ExpectedStockResult> expectedResults;

    /** The expected geometric mean for the test data */
    private Double expectedGMean;

    /**
     * Tests {@link StockExchangeServiceImpl#calculateDividendYield(StockSymbol)},
     * {@link StockExchangeServiceImpl#calculatePERatio(StockSymbol)},
     * {@link StockExchangeServiceImpl#calculateStockPrice(StockSymbol)},
     * {@link StockExchangeServiceImpl#calculateGeometricMean()}.
     *
     * @throws IOException from BufferedReader
     */
    @Test
    public void testStockProperties() throws IOException {
        loadTests();

        for (StockSymbol stockSymbol : this.expectedResults.keySet()) {
            ExpectedStockResult expectedResult = this.expectedResults.get(stockSymbol);
            Assert.assertEquals(expectedResult.dividendYield, this.service.calculateDividendYield(stockSymbol));
            Assert.assertEquals(expectedResult.peRatio, this.service.calculatePERatio(stockSymbol));
            Assert.assertEquals(expectedResult.stockPrice, this.service.calculateStockPrice(stockSymbol));
        }

        Assert.assertEquals(this.expectedGMean, this.service.calculateGeometricMean());
    }

    /**
     * Reads the stock data from the test file and adds it to the service.
     */
    private void loadTests() {
        this.service = new StockExchangeServiceImpl();
        this.expectedResults = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/stockExchangeTests"))) {
            String line;
            StockSymbol stockSymbol = null;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }

                if (line.startsWith("STOCK")) {
                    // This line describes a stock
                    stockSymbol = processStock(line);
                } else if (line.startsWith("GM")) {
                    // This line contains the expected geometric mean
                    String[] fields = line.split(" ");
                    this.expectedGMean = Double.valueOf(fields[1]);
                } else if (line.startsWith("RESULT")) {
                    // This line contains the expected stock calculation results
                    processExpectedResults(line, stockSymbol);
                } else {
                    // This line contains a list of transactions for the previous stock
                    processTransactions(line, stockSymbol);
                }
            }
        } catch (IOException ioe) {
            Assert.fail("IOException: " + ioe.getMessage());
        }
    }

    /**
     * Processes a line in the test file containing the details of a stock
     *
     * @param line the line to process
     * @return the symbol of the processed stock
     */
    private StockSymbol processStock(final String line) {
        String[] stockDetails = line.split(" ");
        StockSymbol stockSymbol = StockSymbol.valueOf(stockDetails[1]);
        boolean isPreferred = "Preferred".equalsIgnoreCase(stockDetails[2]);
        double lastDividend = Double.valueOf(stockDetails[3]);
        double parValue = Double.valueOf(stockDetails[4]);
        if (isPreferred) {
            double fixedDividend = Double.valueOf(stockDetails[5]);
            this.service.createPreferredStock(stockSymbol, parValue, lastDividend, fixedDividend);
        } else {
            this.service.createCommonStock(stockSymbol, parValue, lastDividend);
        }
        this.expectedResults.put(stockSymbol, new ExpectedStockResult());

        return stockSymbol;
    }

    /**
     * Processes a line in the test file containing the transactions for a stock
     *
     * @param line the line to process
     * @param stockSymbol the corresponding stock symbol
     */
    private void processTransactions(final String line, final StockSymbol stockSymbol) {
        String[] fields = line.split(" ");
        int txStartIndex = 0;
        while (txStartIndex < fields.length) {
            TransactionType type = TransactionType.valueOf(fields[txStartIndex]);
            long quantity = Long.parseLong(fields[txStartIndex + 1]);
            double price = Double.parseDouble(fields[txStartIndex + 2]);
            Transaction transaction = new Transaction(quantity, type, price);
            this.service.addTransaction(stockSymbol, transaction);
            txStartIndex += 3;
        }
    }

    /**
     * Processes a line in the test file containing the expected calculation results for a stock
     *
     * @param line the line to process
     * @param stockSymbol the corresponding stock symbol
     */
    private void processExpectedResults(final String line, final StockSymbol stockSymbol) {
        ExpectedStockResult expectedResult = this.expectedResults.get(stockSymbol);
        String[] fields = line.split(" ");
        expectedResult.dividendYield = Double.parseDouble(fields[1]);
        expectedResult.peRatio = Double.parseDouble(fields[2]);
        expectedResult.stockPrice = Double.parseDouble(fields[3]);
    }
}
