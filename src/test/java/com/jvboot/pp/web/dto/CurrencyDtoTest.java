package com.jvboot.pp.web.dto;

import com.jvboot.pp.domain.Currency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyDtoTest {
    static final String KNOWN_TICKER = "BTC";
    static Validator validator;
    static Currency entity;

    CurrencyDto dto;

    @BeforeAll
    static void setEntityAndValidator() {
        entity = new Currency();
        entity.setTicker(KNOWN_TICKER);
        entity.setName("BitCoin");
        entity.setNumberOfCoins(new BigInteger("16770000"));
        entity.setMarketCap(new BigInteger("189580000000"));

        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    void setDto() {
        dto = new CurrencyDto();
        dto.setTicker(KNOWN_TICKER);
        dto.setName("BitCoin");
        dto.setNumberOfCoins("16770000");
        dto.setMarketCap("189580000000");
    }

    @Test
    void constructorFromEntityShouldCreateDto() {
        CurrencyDto entityDto = new CurrencyDto(entity);

        assertThat(entityDto.getTicker()).isEqualTo(entity.getTicker());
        assertThat(entityDto.getName()).isEqualTo(entity.getName());
        assertThat(entityDto.getNumberOfCoins()).isEqualTo(entity.getNumberOfCoins().toString());
        assertThat(entityDto.getMarketCap()).isEqualTo(entity.getMarketCap().toString());
    }

    @Test
    void constructorFromEntityShouldCreateSameEntity() {
        assertThat(new CurrencyDto(entity).toEntity()).isEqualTo(entity);
    }

    @Test
    void toEntityShouldCreateCurrency() {
        Currency currency = dto.toEntity();

        assertThat(currency.getTicker()).isEqualTo(dto.getTicker());
        assertThat(currency.getName()).isEqualTo(dto.getName());
        assertThat(currency.getNumberOfCoins()).isEqualTo(new BigInteger(dto.getNumberOfCoins()));
        assertThat(currency.getMarketCap()).isEqualTo(new BigInteger(dto.getMarketCap()));
    }

    @Test
    void tickerMustBeBetween1And10Letters() {
        assertPass();

        assertTickerPass(dto.getTicker().toLowerCase());
        assertTickerPass("A");
        assertTickerPass("ABCDEFGHIJ");

        assertTickerViolation(null);
        assertTickerViolation("");
        assertTickerViolation("ABCDEFGHIJK");
        assertTickerViolation("AB3");
        assertTickerViolation("ÉÈ");
    }

    private void assertTickerPass(String ticker) {
        dto.setTicker(ticker);
        assertPass();
    }

    private void assertTickerViolation(String ticker) {
        dto.setTicker(ticker);
        assertViolation();
    }

    private void assertPass() {
        assertThat(validator.validate(dto)).isEmpty();
    }

    private void assertViolation() {
        assertThat(validator.validate(dto)).isNotEmpty();
    }
}