package com.cristina.interview.jpmorgan.stocks;

/**
 * A stock which uses the common formula for calculating the dividend yield.
 *
 * @author CristinaGroapa
 */
public class CommonStock extends AbstractStock {

    /**
     * Constructor with arguments.
     * 
     * @param symbol this stock's symbol
     * @param parValue this stock's par value
     * @param lastDividend this stock's last dividend
     */
    public CommonStock(final StockSymbol symbol, final double parValue, final double lastDividend) {
        super(symbol, parValue, lastDividend);
    }

    @Override
    public Double calculateDividendYield() {
        if (hasTransactions()) {
            return getLastDividend() / getTickerPrice();
        }
        return Double.NaN;
    }
}
