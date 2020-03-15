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

    /**
     * Create a new Currency based on the entity passed to it.
     *
     * @param create the Currency to create
     * @return the created Currency
     * @throws ConflictException when a currency with the same ticker already exists
     */
    public Currency create(Currency create) {
        assert create != null;

        String ticker = create.getTicker();
        repository.findById(ticker)
            .ifPresent(currency -> {
                throw new ConflictException("currency already exists for this ticker: " + ticker);
            });

        return repository.saveAndFlush(create);
    }

    /**
     * Delete a Currency by its ticker.
     *
     * @param ticker the ticker of the Currency to delete
     * @throws NotFoundException when the currency for the given ticker cannot be found
     */
    public void deleteByTicker(String ticker) {
        assert ticker != null;

        repository.delete(getByTicker(ticker));
    }

    /**
     * Get a Currency by its ticker.
     *
     * @param ticker the ticker of the Currency to find
     * @throws NotFoundException when the currency for the given ticker cannot be found
     */
    public Currency getByTicker(String ticker) {
        assert ticker != null;

        return repository.findById(ticker.toUpperCase())
            .orElseThrow(() -> new NotFoundException("Could not find currency with ticker: " + ticker));
    }

    /**
     * Get a paginated list of currencies.
     *
     * @param pageable the Pageable for determining offsets, limits and sort orders
     * @return the paginated list of currencies
     */
    public Page<Currency> getPaged(Pageable pageable) {
        assert pageable != null;

        return repository.findAll(pageable);
    }

    /**
     * Update the Currency for the given ticker to the values of the Currency passed to it.
     *
     * @param ticker the ticker of the Currency to update
     * @param update the Currency with the new values
     * @return the updated Currency
     */
    public Currency updateByTicker(String ticker, Currency update) {
        assert ticker != null;
        assert update != null;

        Currency currency = getByTicker(ticker);
        currency.setName(update.getName());
        currency.setNumberOfCoins(update.getNumberOfCoins());
        currency.setMarketCap(update.getMarketCap());

        return repository.saveAndFlush(currency);
    }
}
