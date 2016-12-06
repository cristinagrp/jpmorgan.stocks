package com.cristina.interview.jpmorgan.stocks;

/**
 * A stock which uses the preferred formula for calculating the dividend yield.
 *
 * @author CristinaGroapa
 */
public class PreferredStock extends AbstractStock {

    /** This stock's fixed dividend */
    private final double fixedDividend;

    /**
     * Constructor with arguments.
     * 
     * @param symbol this stock's symbol
     * @param parValue this stock's par value
     * @param lastDividend thsi stock's last dividend
     * @param fixedDividend this stock's fixed dividend
     */
    public PreferredStock(final StockSymbol symbol, final double parValue, final double lastDividend,
            final double fixedDividend) {
        super(symbol, parValue, lastDividend);
        this.fixedDividend = fixedDividend;
    }

    @Override
    public Double calculateDividendYield() {
        if (hasTransactions()) {
            return (this.fixedDividend * getParValue()) / getTickerPrice();
        }
        return Double.NaN;
    }
}
