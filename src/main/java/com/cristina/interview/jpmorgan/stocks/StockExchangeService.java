package com.cristina.interview.jpmorgan.stocks;

/**
 * Contract for stock exchange operations service.
 *
 * @author CristinaGroapa
 */
interface StockExchangeService {

    /**
     * Creates a common stock with the given details and adds it to the internal register.
     * 
     * @param stockSymbol the stock symbol
     * @param parValue the par value of the stock
     * @param lastDividend the last dividend of the stock
     */
    void createCommonStock(StockSymbol stockSymbol, double parValue, double lastDividend);

    /**
     * Creates a preferred stock with the given details and adds it to the internal register.
     * 
     * @param stockSymbol the stock symbol
     * @param parValue the par value of the stock
     * @param lastDividend the last dividend of the stock
     * @param fixedDividend the stock's fixed dividend
     */
    void createPreferredStock(StockSymbol stockSymbol, double parValue, double lastDividend, double fixedDividend);

    /**
     * Calculates the geometric mean of all the registered stocks with valid stock prices.
     * 
     * @return the geometric mean of the existing stocks or {@link Double#NaN} if not enough data is available to
     *         calculate
     */
    Double calculateGeometricMean();

    /**
     * Adds a new transaction to a registered stock. If the stock does not exist, it does nothing.
     * 
     * @param stockSymbol the stock to which the transaction belongs
     * @param transaction the transaction to add
     */
    void addTransaction(StockSymbol stockSymbol, Transaction transaction);

    /**
     * Calculates the dividend yield for a given stock.
     * 
     * @param stockSymbol the identifier of the stock for which to calculate the dividend yield
     * @return the dividend yield, or {@link Double#NaN} if not enough data is available for the stock
     */
    Double calculateDividendYield(StockSymbol stockSymbol);

    /**
     * Calculates the P/E Ratio for a given stock.
     * 
     * @param stockSymbol the identifier of the stock for which to calculate the P/E Ratio
     * @return the P/E Ratio, or {@link Double#NaN} if not enough data is available for the stock
     */
    Double calculatePERatio(StockSymbol stockSymbol);

    /**
     * Calculates the stock price for a given stock.
     * 
     * @param stockSymbol the identifier of the stock for which to calculate the price
     * @return the stock price, or {@link Double#NaN} if not enough data is available for the stock
     */
    Double calculateStockPrice(StockSymbol stockSymbol);
}
