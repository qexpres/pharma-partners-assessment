package com.jvboot.pp.web.dto;

import com.jvboot.pp.domain.Currency;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigInteger;

public class CurrencyDto {
    @NotBlank
    @Size(min = 3, max = 3, message = "must be exactly 3 characters long")
    private String ticker;
    @NotBlank
    protected String name;
    @NotBlank
    @Pattern(regexp = "[\\d]+", message = "must be a positive numeric string")
    protected String numberOfCoins;
    @NotBlank
    @Pattern(regexp = "[\\d]+", message = "must be a positive numeric string")
    protected String marketCap;

    public CurrencyDto() {
    }

    public CurrencyDto(Currency entity) {
        this.ticker = entity.getTicker();
        this.name = entity.getName();
        this.numberOfCoins = entity.getNumberOfCoins().toString();
        this.marketCap = entity.getMarketCap().toString();
    }

    public Currency toEntity() {
        Currency entity = new Currency();
        entity.setTicker(ticker.toUpperCase());
        entity.setName(name);
        entity.setNumberOfCoins(new BigInteger(numberOfCoins));
        entity.setMarketCap(new BigInteger(marketCap));
        return entity;
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

    public String getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setNumberOfCoins(String numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }
}
