package com.jvboot.pp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table
public class Currency {
    @Id
    private String ticker;
    private String name;
    @Column(name = "number_of_coins")
    private BigInteger numberOfCoins;
    @Column(name = "market_cap")
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
}
