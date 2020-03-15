package com.jvboot.pp.web.dto;

import com.jvboot.pp.domain.Currency;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CurrencyDto extends BaseCurrencyDto {
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{1,10}", message = "must consist of 1 to 10 letters")
    private String ticker;

    public CurrencyDto() {
    }

    /**
     * Create a CurrencyDto from a Currency entity.
     *
     * @param entity the Currency entity
     */
    public CurrencyDto(Currency entity) {
        setTicker(entity.getTicker());
        setName(entity.getName());
        setNumberOfCoins(entity.getNumberOfCoins().toString());
        setMarketCap(entity.getMarketCap().toString());
    }

    @Override
    public Currency toEntity() {
        Currency entity = super.toEntity();
        entity.setTicker(ticker.toUpperCase());
        return entity;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
