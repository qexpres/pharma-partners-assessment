package com.jvboot.pp.service;

import com.jvboot.pp.domain.Currency;
import com.jvboot.pp.exception.ConflictException;
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

    public Currency updateByTicker(String ticker, Currency update) {
        Currency currency = getByTicker(ticker);
        currency.setName(update.getName());
        currency.setNumberOfCoins(update.getNumberOfCoins());
        currency.setMarketCap(update.getMarketCap());
        return repository.saveAndFlush(currency);
    }

    public Currency create(Currency create) {
        String ticker = create.getTicker();
        repository.findById(ticker)
            .ifPresent(currency -> {
                throw new ConflictException("currency already exists for this ticker: " + ticker);
            });

        return repository.saveAndFlush(create);
    }
}
