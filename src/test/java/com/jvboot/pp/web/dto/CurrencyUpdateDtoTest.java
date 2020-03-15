package com.jvboot.pp.web.dto;

import com.jvboot.pp.domain.Currency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyUpdateDtoTest {
    static Validator validator;

    CurrencyUpdateDto dto;

    @BeforeAll
    static void setValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    void setDto() {
        dto = new CurrencyUpdateDto();
        dto.setName("BitCoin");
        dto.setNumberOfCoins("16770000");
        dto.setMarketCap("189580000000");

        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void toEntityShouldCreateCurrency() {
        Currency currency = dto.toEntity();

        assertThat(currency.getName()).isEqualTo(dto.getName());
        assertThat(currency.getNumberOfCoins()).isEqualTo(new BigInteger(dto.getNumberOfCoins()));
        assertThat(currency.getMarketCap()).isEqualTo(new BigInteger(dto.getMarketCap()));
    }

    @Test
    void nameCannotBeEmpty() {
        assertPass();

        assertNamePass("Bit");
        assertNamePass("Bit1337");
        assertNamePass("Pièce d'Hôpital");

        assertNameViolation(null);
        assertNameViolation("");
    }

    @Test
    void numberOfCoinsMustBePositiveStringInteger() {
        assertPass();

        assertNumberOfCoinsPass("1");
        assertNumberOfCoinsPass("51241");

        assertNumberOfCoinsViolation(null);
        assertNumberOfCoinsViolation("");
        assertNumberOfCoinsViolation("-1");
        assertNumberOfCoinsViolation("3.14");
        assertNumberOfCoinsViolation("e");
    }

    @Test
    void marketCapBePositiveStringInteger() {
        assertPass();

        assertMarketCapPass("1");
        assertMarketCapPass("51241");

        assertMarketCapViolation(null);
        assertMarketCapViolation("");
        assertMarketCapViolation("-1");
        assertMarketCapViolation("3.14");
        assertMarketCapViolation("e");
    }

    private void assertNamePass(String name) {
        dto.setName(name);
        assertPass();
    }

    private void assertNameViolation(String name) {
        dto.setName(name);
        assertViolation();
    }

    private void assertNumberOfCoinsPass(String numberOfCoins) {
        dto.setNumberOfCoins(numberOfCoins);
        assertPass();
    }

    private void assertNumberOfCoinsViolation(String numberOfCoins) {
        dto.setNumberOfCoins(numberOfCoins);
        assertViolation();
    }

    private void assertMarketCapPass(String marketCap) {
        dto.setMarketCap(marketCap);
        assertPass();
    }

    private void assertMarketCapViolation(String marketCap) {
        dto.setMarketCap(marketCap);
        assertViolation();
    }

    private void assertPass() {
        assertThat(validator.validate(dto)).isEmpty();
    }

    private void assertViolation() {
        assertThat(validator.validate(dto)).isNotEmpty();
    }
}