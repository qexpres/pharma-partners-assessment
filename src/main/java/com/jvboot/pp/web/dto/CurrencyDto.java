package com.jvboot.pp.web.dto;

import com.jvboot.pp.domain.Currency;

import java.math.BigInteger;

public class CurrencyDto {
    private String ticker;
    private String name;
    private BigInteger numberOfCoins;
    private BigInteger marketCap;

    public CurrencyDto(Currency entity) {
        this.ticker = entity.getTicker();
        this.name = entity.getName();
        this.numberOfCoins = entity.getNumberOfCoins();
        this.marketCap = entity.getMarketCap();
    }

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
