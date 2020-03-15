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
    static final String NEW_TICKER = "ABC";
    static final String KNOWN_TICKER = "BTC";

    Currency newCurrency;

    @Autowired
    CurrencyService service;

    @BeforeEach
    void setCurrency() {
        newCurrency = new Currency();
        newCurrency.setTicker(NEW_TICKER);
        newCurrency.setName("AlphaBetaCoin");
        newCurrency.setMarketCap(new BigInteger("123456789"));
        newCurrency.setNumberOfCoins(new BigInteger("987654321"));
    }

    @Test
    @DirtiesContext
    void createShouldCreateCurrency() {
        Currency createdCurrency = service.create(newCurrency);
        assertThat(createdCurrency).isEqualTo(createdCurrency);

        Currency persistedCurrency = service.getByTicker(newCurrency.getTicker());
        assertThat(persistedCurrency).isEqualTo(newCurrency);
    }

    @Test
    @DirtiesContext
    void createWithKnownTickerShouldThrowConflict() {
        newCurrency.setTicker(KNOWN_TICKER);

        ConflictException ex = assertThrows(ConflictException.class, () -> service.create(newCurrency));
        assertThat(ex.getMessage()).isEqualTo("currency already exists for ticker " + KNOWN_TICKER);
    }

    @Test
    @DirtiesContext
    void deleteCurrencyWithKnownTickerShouldDeleteCurrency() {
        service.deleteByTicker(KNOWN_TICKER);
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.deleteByTicker(KNOWN_TICKER));
        assertThat(ex.getMessage()).isEqualTo("could not find currency with ticker " + KNOWN_TICKER);
    }

    @Test
    void deleteCurrencyWithUnknownTickerShouldThrowNotFound() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.deleteByTicker(NEW_TICKER));
        assertThat(ex.getMessage()).isEqualTo("could not find currency with ticker " + NEW_TICKER);
    }

    @Test
    void getCurrencyWithKnownTickerShouldReturnCurrency() {
        Currency currency = service.getByTicker(KNOWN_TICKER);
        assertThat(currency)
            .isNotNull()
            .matches(c -> KNOWN_TICKER.matches(c.getTicker()));
    }

    @Test
    void getCurrencyWithUnknownTickerShouldThrowNotFound() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getByTicker(NEW_TICKER));
        assertThat(ex.getMessage()).isEqualTo("could not find currency with ticker " + NEW_TICKER);
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
        Currency originalCurrency = service.getByTicker(KNOWN_TICKER);

        Currency currency = shallowClone(originalCurrency);
        currency.setName(currency.getName() + " v2");
        currency.setNumberOfCoins(currency.getNumberOfCoins().add(BigInteger.ONE));
        currency.setMarketCap(currency.getMarketCap().subtract(BigInteger.TEN));

        Currency updatedCurrency = service.updateByTicker(KNOWN_TICKER, currency);

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
        Currency currency = service.getByTicker(KNOWN_TICKER);
        currency.setTicker(NEW_TICKER);

        Currency updatedCurrency = service.updateByTicker(KNOWN_TICKER, currency);

        assertThat(updatedCurrency.getTicker())
            .isNotEqualTo(currency.getTicker())
            .isEqualTo(KNOWN_TICKER);
    }

    private Currency shallowClone(Currency currency) {
        Currency cloned = new Currency();
        cloned.setTicker(currency.getTicker());
        cloned.setName(currency.getName());
        cloned.setMarketCap(currency.getMarketCap());
        cloned.setNumberOfCoins(currency.getNumberOfCoins());
        return cloned;
    }
}