package com.jvboot.pp.web.controller;

import com.jvboot.pp.annotation.ApiPageable;
import com.jvboot.pp.service.CurrencyService;
import com.jvboot.pp.web.dto.CurrencyDto;
import com.jvboot.pp.web.dto.CurrencyUpdateDto;
import com.jvboot.pp.web.dto.DataDto;
import com.jvboot.pp.web.dto.PagedDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "Currencies")
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
    @ResponseStatus(HttpStatus.CREATED)
    public DataDto<CurrencyDto> createCurrency(@Valid @RequestBody CurrencyDto createDto) {
        return new DataDto<>(new CurrencyDto(service.create(createDto.toEntity())));
    }

    @DeleteMapping("/{ticker}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrencyByTicker(@PathVariable String ticker) {
        service.deleteByTicker(ticker);
    }

    @GetMapping
    @ApiPageable
    public PagedDto<CurrencyDto> getCurrencies(@ApiIgnore Pageable pageable) {
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
