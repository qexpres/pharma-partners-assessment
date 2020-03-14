package com.jvboot.pp.web.controller;

import com.jvboot.pp.service.CurrencyService;
import com.jvboot.pp.web.dto.CurrencyDto;
import com.jvboot.pp.web.dto.PagedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
