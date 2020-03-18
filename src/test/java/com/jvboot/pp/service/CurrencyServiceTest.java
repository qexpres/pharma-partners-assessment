package com.jvboot.pp.service;

import com.jvboot.pp.domain.Currency;
import com.jvboot.pp.exception.ConflictException;
import com.jvboot.pp.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class CurrencyServiceTest {
    static final String ticker = "ABC";
    static final String name = "AlphaBetaCoin";
    static final String numberOfCoins = "987654321";
    static final String marketCap = "123456789";

    static final String knownTicker = "BTC";

    Currency newCurrency;

    @Autowired
    CurrencyService service;

    @BeforeEach
    void setCurrency() {
        newCurrency = new Currency.CurrencyBuilder(name, new BigInteger(numberOfCoins), new BigInteger(marketCap))
            .setTicker(ticker)
            .build();
    }

    @Test
    @DirtiesContext
    void createShouldCreateCurrency() {
        Currency createdCurrency = service.create(newCurrency);
        assertThat(createdCurrency).isEqualTo(newCurrency);

        Currency persistedCurrency = service.getByTicker(newCurrency.getTicker());
        assertThat(persistedCurrency).isEqualTo(newCurrency);
    }

    @Test
    @DirtiesContext
    void createWithKnownTickerShouldThrowConflict() {
        newCurrency.setTicker(knownTicker);

        ConflictException ex = assertThrows(ConflictException.class, () -> service.create(newCurrency));
        assertThat(ex.getMessage()).isEqualTo("currency already exists for ticker " + knownTicker);
    }

    @Test
    @DirtiesContext
    void deleteCurrencyWithKnownTickerShouldDeleteCurrency() {
        service.deleteByTicker(knownTicker);
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.deleteByTicker(knownTicker));
        assertThat(ex.getMessage()).isEqualTo("could not find currency with ticker " + knownTicker);
    }

    @Test
    void deleteCurrencyWithUnknownTickerShouldThrowNotFound() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.deleteByTicker(ticker));
        assertThat(ex.getMessage()).isEqualTo("could not find currency with ticker " + ticker);
    }

    @Test
    void getCurrencyWithKnownTickerShouldReturnCurrency() {
        Currency currency = service.getByTicker(knownTicker);
        assertThat(currency)
            .isNotNull()
            .matches(c -> knownTicker.matches(c.getTicker()));
    }

    @Test
    void getCurrencyWithUnknownTickerShouldThrowNotFound() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getByTicker(ticker));
        assertThat(ex.getMessage()).isEqualTo("could not find currency with ticker " + ticker);
    }

    @Test
    void getPagedShouldReturnPagedCurrencies() {
        Pageable pageable = PageRequest.of(1, 2);
        Page<Currency> page = service.getPaged(pageable);
        assertThat(page).isNotNull().isNotEmpty();
        assertThat(page.getSize()).isLessThanOrEqualTo(pageable.getPageSize());
        assertThat(page.getNumber()).isEqualTo(pageable.getPageNumber());
    }

    @Test
    void getPagedWithOutOfRangePageableShouldReturnEmptyResponse() {
        Pageable pageable = PageRequest.of(2, 10);
        Page<Currency> page = service.getPaged(pageable);
        assertThat(page).isNotNull().isEmpty();
        assertThat(page.getNumber()).isEqualTo(pageable.getPageNumber());
    }

    @Test
    @DirtiesContext
    void updateByTickerShouldUpdate() {
        Currency originalCurrency = service.getByTicker(knownTicker);

        Currency currency = shallowClone(originalCurrency);
        currency.setName(currency.getName() + " v2");
        currency.setNumberOfCoins(currency.getNumberOfCoins().add(BigInteger.ONE));
        currency.setMarketCap(currency.getMarketCap().subtract(BigInteger.TEN));

        Currency updatedCurrency = service.updateByTicker(knownTicker, currency);

        assertThat(updatedCurrency)
            .isEqualTo(originalCurrency)
            .isEqualTo(currency);
        assertThat(updatedCurrency.getName())
            .isEqualTo(currency.getName())
            .isNotEqualTo(originalCurrency.getName());
        assertThat(updatedCurrency.getMarketCap())
            .isEqualTo(currency.getMarketCap())
            .isNotEqualTo(originalCurrency.getMarketCap());
        assertThat(updatedCurrency.getNumberOfCoins())
            .isEqualTo(currency.getNumberOfCoins())
            .isNotEqualTo(originalCurrency.getNumberOfCoins());
    }

    @Test
    void updateByTickerShouldNotUpdateTicker() {
        Currency currency = service.getByTicker(knownTicker);
        currency.setTicker(ticker);

        Currency updatedCurrency = service.updateByTicker(knownTicker, currency);

        assertThat(updatedCurrency.getTicker())
            .isNotEqualTo(currency.getTicker())
            .isEqualTo(knownTicker);
    }

    private Currency shallowClone(Currency currency) {
        return new Currency.CurrencyBuilder(currency.getName(), currency.getNumberOfCoins(), currency.getMarketCap())
            .setTicker(currency.getTicker())
            .build();
    }
}