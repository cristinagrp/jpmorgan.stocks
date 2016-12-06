package com.cristina.interview.jpmorgan.stocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link StockExchangeService} implementation for Global Beverage Corporation Exchange.
 * Initially holds the following stocks:
 * <ul>
 * <li>TEA</li>
 * <li>POP</li>
 * <li>ALE</li>
 * <li>GIN</li>
 * <li>JOE</li>
 * </ul>
 *
 * @author CristinaGroapa
 */
public class StockExchangeServiceImplGBCE extends StockExchangeServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockExchangeServiceImplGBCE.class);

    public StockExchangeServiceImplGBCE() {
        super(LOGGER);
        createCommonStock(StockSymbol.TEA, 100.0, 0.0);
        createCommonStock(StockSymbol.POP, 100.0, 8.0);
        createCommonStock(StockSymbol.ALE, 60.0, 23.0);
        createPreferredStock(StockSymbol.GIN, 100.0, 8.0, 0.02);
        createCommonStock(StockSymbol.JOE, 250.0, 13.0);
    }
}
