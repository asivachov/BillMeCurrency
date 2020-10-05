package me.bill.currency.service;

import me.bill.currency.domain.Currency;
import me.bill.currency.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    private final WikiCurrencyLoader currencyLoader;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(WikiCurrencyLoader currencyLoader, CurrencyRepository currencyRepository) {
        this.currencyLoader = currencyLoader;
        this.currencyRepository = currencyRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadToDb() throws IOException {
        List<Currency> currencies = currencyLoader.load();
        currencyRepository.saveAll(currencies);
    }

    public Optional<Currency> findByCode(String code) {
        return currencyRepository.findByCode(code);
    }


}
