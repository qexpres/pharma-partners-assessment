package com.jvboot.pp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table
public class Currency implements Serializable {
    @Id
    @Column(name = "TICKER")
    private String ticker;
    @Column(name = "NAME")
    private String name;
    @Column(name = "NUMBER_OF_COINS")
    private BigInteger numberOfCoins;
    @Column(name = "MARKET_CAP")
    private BigInteger marketCap;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setNumberOfCoins(BigInteger numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public BigInteger getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigInteger marketCap) {
        this.marketCap = marketCap;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ticker);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Currency)) {
            return false;
        }
        return Objects.equals(this.ticker, ((Currency) obj).ticker);
    }
}
