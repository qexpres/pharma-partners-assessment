package com.jvboot.pp.web.controller;

import com.jvboot.pp.service.CurrencyService;
import com.jvboot.pp.web.dto.CurrencyDto;
import com.jvboot.pp.web.dto.DataDto;
import com.jvboot.pp.web.dto.PagedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/currencies")
public class CurrencyController {
    private final CurrencyService service;

    @Autowired
    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    public PagedDto<CurrencyDto> getCurrencies(Pageable pageable) {
        return new PagedDto<>(service.getPaged(pageable).map(CurrencyDto::new));
    }

    @GetMapping("/{ticker}")
    public DataDto<CurrencyDto> getCurrencyByTicker(@PathVariable String ticker) {
        return new DataDto<>(new CurrencyDto(service.getByTicker(ticker)));
    }
}
