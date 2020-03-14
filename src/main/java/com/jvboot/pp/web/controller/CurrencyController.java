package com.jvboot.pp.web.controller;

import com.jvboot.pp.service.CurrencyService;
import com.jvboot.pp.web.dto.CurrencyDto;
import com.jvboot.pp.web.dto.CurrencyUpdateDto;
import com.jvboot.pp.web.dto.DataDto;
import com.jvboot.pp.web.dto.PagedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(path = "/currencies")
public class CurrencyController {
    private final CurrencyService service;

    @Autowired
    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @PostMapping
    public DataDto<CurrencyDto> createCurrency(@Valid @RequestBody CurrencyDto createDto) {
        return new DataDto<>(new CurrencyDto(service.create(createDto.toEntity())));
    }

    @GetMapping
    public PagedDto<CurrencyDto> getCurrencies(Pageable pageable) {
        return new PagedDto<>(service.getPaged(pageable).map(CurrencyDto::new));
    }

    @GetMapping("/{ticker}")
    public DataDto<CurrencyDto> getCurrencyByTicker(@PathVariable String ticker) {
        return new DataDto<>(new CurrencyDto(service.getByTicker(ticker)));
    }

    @PutMapping("/{ticker}")
    public DataDto<CurrencyDto> updateCurrencyByTicker(
        @PathVariable String ticker,
        @Valid @RequestBody CurrencyUpdateDto updateDto
    ) {
        return new DataDto<>(new CurrencyDto(service.updateByTicker(ticker, updateDto.toEntity())));
    }
}
