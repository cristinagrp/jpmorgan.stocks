package com.cristina.interview.jpmorgan.stocks;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A general {@link StockExchangeService} implementation.
 *
 * @author CristinaGroapa
 */
public class StockExchangeServiceImpl implements StockExchangeService {

    /** The logger for this class */
    private final Logger logger;

    /** A map of registered stocks */
    private final Map<StockSymbol, AbstractStock> stocks;

    /**
     * Default constructor
     */
    public StockExchangeServiceImpl() {
        this(LoggerFactory.getLogger(StockExchangeServiceImpl.class));
    }

    /**
     * Constructor that receives a logger.
     *
     * @param logger the logger to be used by this instance
     */
    protected StockExchangeServiceImpl(final Logger logger) {
        this.logger = logger;
        this.stocks = new HashMap<>();

        logger.info("Initialised service");
    }

    @Override
    public Double calculateGeometricMean() {
        double product = 1.0;
        int count = 0;
        for (AbstractStock stock : this.stocks.values()) {
            Double price = stock.calculateStockPrice();
            if (!price.isNaN()) {
                product *= price;
                count++;
            }
        }
        if (count > 0) {
            return roundDouble(Math.pow(product, (double) 1 / count));
        }

        this.logger.info("Asked to calculate geometric mean of stocks, but no valid stock price available.");
        return Double.NaN;
    }

    @Override
    public void addTransaction(final StockSymbol stockSymbol, final Transaction transaction) {
        AbstractStock stock = this.stocks.get(stockSymbol);
        if (stock != null) {
            stock.addTransaction(transaction);
            this.logger.debug("Adding transaction for stock {}", stockSymbol);
        } else {
            this.logger.info("Asked to add transaction for non-existing stock {}. Nothing to do.", stockSymbol);
        }
    }

    @Override
    public Double calculateDividendYield(final StockSymbol stockSymbol) {
        AbstractStock stock = this.stocks.get(stockSymbol);
        if (stock != null) {
            return roundDouble(stock.calculateDividendYield());
        }
        this.logger.info("Asked to calculate dividend yield for non-existing stock {}. Returning NaN.", stockSymbol);
        return Double.NaN;
    }

    @Override
    public Double calculatePERatio(final StockSymbol stockSymbol) {
        AbstractStock stock = this.stocks.get(stockSymbol);
        if (stock != null) {
            return roundDouble(stock.calculatePERatio());
        }
        this.logger.info("Asked to calculate P/E Ratio for non-existing stock {}. Returning NaN.", stockSymbol);
        return Double.NaN;
    }

    @Override
    public Double calculateStockPrice(final StockSymbol stockSymbol) {
        AbstractStock stock = this.stocks.get(stockSymbol);
        if (stock != null) {
            return roundDouble(stock.calculateStockPrice());
        }
        this.logger.info("Asked to calculate stock price for non-existing stock {}. Returning NaN.", stockSymbol);
        return Double.NaN;
    }

    @Override
    public void createCommonStock(final StockSymbol stockSymbol, final double parValue, final double lastDividend) {
        this.logger.info("Creating common stock {} with parValue={}, lastDividend={}.", stockSymbol, parValue,
                         lastDividend);
        this.stocks.put(stockSymbol, new CommonStock(stockSymbol, parValue, lastDividend));
    }

    @Override
    public void createPreferredStock(final StockSymbol stockSymbol, final double parValue, final double lastDividend,
            final double fixedDividend) {
        this.logger.info("Creating preferred stock {} with parValue={}, lastDividend={}, fixedDividend={}.",
                         stockSymbol, parValue, lastDividend, fixedDividend);
        this.stocks.put(stockSymbol, new PreferredStock(stockSymbol, parValue, lastDividend, fixedDividend));
    }

    /**
     * Rounds a given double value to 2 decimals. In the current implementation the number of decimals is hardcoded, but
     * it can easily be made configurable.
     *
     * @param value the value to round
     * @return the double value rounded to 2 decimals
     */
    protected Double roundDouble(final Double value) {
        if (value.isNaN()) {
            return value;
        }
        return Math.floor(value * 100) / 100d;
    }
}
