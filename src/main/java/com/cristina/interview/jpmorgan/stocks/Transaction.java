package com.cristina.interview.jpmorgan.stocks;

import java.util.Date;

/**
 * Represents a stock transaction and holds its details.
 *
 * @author CristinaGroapa
 */
public class Transaction {

    /** Type of a stock transaction based on its direction - i.e. buy/sell */
    public enum TransactionType {
        /** The transaction is a buy */
        BUY,
        /** The transaction is a sell */
        SELL
    };

    /** The transaction timestamp */
    private final Date timestamp;

    /** The number of shares transacted */
    private final long quantityOfShares;

    /** The transaction type - i.e. buy or sell */
    private final TransactionType transactionType;

    /** The price per share in this transaction */
    private final double price;

    /**
     * Constructor.
     *
     * @param timestamp the date & time when this transaction occurred
     * @param quantityOfShares the number of shares transacted
     * @param transactionType BUY or SELL
     * @param price the price per share
     */
    public Transaction(final Date timestamp, final long quantityOfShares, final TransactionType transactionType,
            final double price) {
        this.timestamp = new Date(timestamp.getTime());
        this.quantityOfShares = quantityOfShares;
        this.price = price;
        this.transactionType = transactionType;
    }

    /**
     * Constructor. Generates the transaction timestamp assuming it is when the object is created.
     *
     * @param quantityOfShares the number of shares transacted
     * @param transactionType BUY or SELL
     * @param price the price per share
     */
    public Transaction(final long quantityOfShares, final TransactionType transactionType, final double price) {
        this(new Date(), quantityOfShares, transactionType, price);
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return new Date(this.timestamp.getTime());
    }

    /**
     * @return the quantityOfShares
     */
    public long getQuantityOfShares() {
        return this.quantityOfShares;
    }

    /**
     * @return the transactionType
     */
    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return this.price;
    }
}
