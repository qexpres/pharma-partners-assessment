package com.jvboot.pp.service;

import com.jvboot.pp.domain.Currency;
import com.jvboot.pp.exception.NotFoundException;
import com.jvboot.pp.repository.CurrencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    private final CurrencyRepository repository;

    public CurrencyService(CurrencyRepository repository) {
        this.repository = repository;
    }

    public Currency getByTicker(String ticker) {
        return repository.findById(ticker.toUpperCase())
            .orElseThrow(() -> new NotFoundException("Could not find currency with ticker: " + ticker));
    }

    public Page<Currency> getPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
