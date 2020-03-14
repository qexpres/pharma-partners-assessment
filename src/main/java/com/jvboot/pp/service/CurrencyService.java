package com.jvboot.pp.service;

import com.jvboot.pp.domain.Currency;
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

    public Page<Currency> getPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
