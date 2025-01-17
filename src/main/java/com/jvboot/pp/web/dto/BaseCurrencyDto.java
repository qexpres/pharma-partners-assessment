package com.jvboot.pp.web.dto;

import com.jvboot.pp.domain.Currency;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;

public abstract class BaseCurrencyDto {
    @NotBlank
    protected String name;
    @NotBlank
    @Pattern(regexp = "[\\d]+", message = "must be a positive numeric string")
    protected String numberOfCoins;
    @NotBlank
    @Pattern(regexp = "[\\d]+", message = "must be a positive numeric string")
    protected String marketCap;

    public BaseCurrencyDto() {
    }

    /**
     * Create a Currency entity from this DTO.
     *
     * @return the Currency entity
     */
    public Currency toEntity() {
        return toBuilder().build();
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

    /**
     * Create a currency builder for further extension.
     *
     * @return the CurrencyBuilder
     */
    protected Currency.CurrencyBuilder toBuilder() {
        return new Currency.CurrencyBuilder(name, new BigInteger(numberOfCoins), new BigInteger(marketCap));
    }
}
