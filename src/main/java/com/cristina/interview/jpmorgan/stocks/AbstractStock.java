package com.cristina.interview.jpmorgan.stocks;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;

import org.apache.commons.lang3.time.DateUtils;

/**
 * Common representation of a stock.
 *
 * @author CristinaGroapa
 */
public abstract class AbstractStock {

    /** The historical time interval (in minutes) to be taken into consideration for calculating the stock price. */
    private static final int PRICE_TIME_INTERVAL = 15;

    /** This stock's symbol */
    private final StockSymbol symbol;

    /**
     * A record of all the transactions that took place for this stock. The most recent are at the head of the list.
     * <b>NB I assumed that the transactions are added in real-time, i.e. in chronological order, which is why I am not
     * sorting
     * the queue by the timestamp. Otherwise the transaction history could be a sorted collection and Transaction could
     * implement Comparable.</b>
     */
    private final Deque<Transaction> transactionHistory;

    /** This stock's par value */
    private final Double parValue;

    /** This stock's last dividend */
    private Double lastDividend;

    /**
     * Constructor that receives all necessary details to create a new stock.
     *
     * @param symbol the stock's symbol
     * @param parValue the stock's par value
     * @param lastDividend the stock's last dividend
     */
    public AbstractStock(final StockSymbol symbol, final double parValue, final double lastDividend) {
        this.symbol = symbol;
        this.parValue = parValue;
        this.lastDividend = lastDividend;
        this.transactionHistory = new ArrayDeque<>();
    }

    /**
     * Calculates the dividend yield for this stock.
     *
     * @return the dividend yield
     */
    public abstract Double calculateDividendYield();

    /**
     * Calculates the P/E Ratio for this stock.
     *
     * @return the P/E Ratio of this stock, or {@link Double#NaN} if not enough data is available
     */
    public Double calculatePERatio() {
        if (this.transactionHistory.isEmpty() || this.lastDividend == null || this.lastDividend == 0.0) {
            return Double.NaN;
        }

        return getTickerPrice() / getLastDividend();
    }

    /**
     * Calculates the stock price for this stock.
     *
     * @return the stock price of this stock, or {@link Double#NaN} if not enough data is available - i.e. there are no
     *         transactions
     */
    public Double calculateStockPrice() {
        if (this.transactionHistory.isEmpty()) {
            return Double.NaN;
        }

        Date timeIntervalStart = DateUtils.addMinutes(new Date(), -1 * PRICE_TIME_INTERVAL);
        Iterator<Transaction> iterator = this.transactionHistory.iterator();

        double stockPriceSum = 0.0d;
        long stockQuantitySum = 0l;
        boolean intervalFinished = false;

        while (iterator.hasNext() && !intervalFinished) {
            Transaction tx = iterator.next();
            if (tx.getTimestamp().after(timeIntervalStart)) {
                long quantity = tx.getQuantityOfShares();
                double price = tx.getPrice();
                stockPriceSum += quantity * price;
                stockQuantitySum += quantity;
            } else {
                intervalFinished = true;
            }
        }

        return stockPriceSum / stockQuantitySum;
    }

    /**
     * @return this stock's ticker price
     */
    protected double getTickerPrice() {
        return this.transactionHistory.peek().getPrice();
    }

    /**
     * Adds a transaction to the transaction history.
     * <b>NB I assumed that the transactions are added in real-time, i.e. in chronological order, which is why I am not
     * sorting
     * the queue by the timestamp. Otherwise the transaction history could be a sorted list and Transaction could
     * implement Comparable.</b>
     *
     * @param transaction the transaction to add
     */
    public void addTransaction(final Transaction transaction) {
        this.transactionHistory.push(transaction);
    }

    /**
     * @return the lastDividend
     */
    public double getLastDividend() {
        return this.lastDividend;
    }

    /**
     * @param lastDividend the lastDividend to set
     */
    public void setLastDividend(final double lastDividend) {
        this.lastDividend = lastDividend;
    }

    /**
     * @return the symbol
     */
    public StockSymbol getSymbol() {
        return this.symbol;
    }

    /**
     * @return true if the transaction history is not empty, false otherwise
     */
    public boolean hasTransactions() {
        return !this.transactionHistory.isEmpty();
    }

    /**
     * @return the parValue
     */
    public double getParValue() {
        return this.parValue;
    }
}
