package com.jvboot.pp.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {
    private static final String ticker1 = "ABC";
    private static final String name1 = "AlphaBetaCoin";
    private static final String numberOfCoins1 = "987654321";
    private static final String marketCap1 = "123456789";

    private static final String ticker2 = "ABD";
    private static final String name2 = "Better ABC";
    private static final String numberOfCoins2 = "87654321";
    private static final String marketCap2 = "123456";

    private static Currency currency;
    private static Currency dirtyCurrency;
    private static Currency otherCurrency;

    @BeforeAll
    private static void setCurrency() {
        currency = createCurrency(ticker1, name1, numberOfCoins1, marketCap1);
        dirtyCurrency = createCurrency(ticker1, name2, numberOfCoins2, marketCap2);
        otherCurrency = createCurrency(ticker2, name2, numberOfCoins2, marketCap2);
    }

    private static Currency createCurrency(String ticker, String name, String numberOfCoins, String marketCap) {
        return new Currency.CurrencyBuilder(name, new BigInteger(numberOfCoins), new BigInteger(marketCap))
            .setTicker(ticker)
            .build();
    }

    @Test
    public void currencyBuilderShouldCreateCurrency() {
        Currency newCurrency = new Currency.CurrencyBuilder(
            name1,
            new BigInteger(numberOfCoins1),
            new BigInteger(marketCap1)
        ).build();

        assertNull(newCurrency.getTicker());
        assertEquals(currency.getName(), newCurrency.getName());
        assertEquals(currency.getNumberOfCoins(), newCurrency.getNumberOfCoins());
        assertEquals(currency.getMarketCap(), newCurrency.getMarketCap());
    }

    @Test
    public void currencyBuilderShouldSetOptionalTicker() {
        Currency newCurrency = new Currency.CurrencyBuilder(
            name1,
            new BigInteger(numberOfCoins1),
            new BigInteger(marketCap1)
        ).setTicker(ticker1).build();

        assertNotNull(newCurrency.getTicker());
        assertEquals(currency.getTicker(), newCurrency.getTicker());
        assertEquals(currency, newCurrency);
    }

    @Test
    public void currencyShouldEqualSameCurrency() {
        assertEquals(currency, currency);
        assertEquals(currency.hashCode(), currency.hashCode());
    }

    @Test
    public void currencyShouldEqualSameTicker() {
        assertEquals(currency, dirtyCurrency);
        assertEquals(currency.hashCode(), dirtyCurrency.hashCode());
    }

    @Test
    public void currencyShouldNotEqualDifferentTicker() {
        assertNotEquals(currency, otherCurrency);
        assertNotEquals(currency.hashCode(), otherCurrency.hashCode());
    }

    @Test
    public void currencyShouldNotEqualNull() {
        assertNotEquals(currency, null);
    }

    @Test
    public void currencyShouldNotEqualDifferentObject() {
        assertNotEquals(currency, new Object());
    }
}