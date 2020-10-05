package me.bill.currency.service;

import me.bill.currency.domain.Currency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WikiCurrencyLoaderTest {

    private static List<Currency> currencies;

    @BeforeAll
    public static void loadCurrencies() throws IOException {
        WikiCurrencyLoader wikiCurrencyLoader = new WikiCurrencyLoader(new TestWikiCurrencyDocumentDownloader());
        currencies = wikiCurrencyLoader.load();
    }

    @Test
    void testIfAllCurrenciesLoaded() {
        assertEquals(179, currencies.size());
    }

    @Test
    void testIfContainsRuble() {
        assertTrue(containsCode(currencies, "RUB"));
    }

    @Test
    void testIfContainsUsd() {
        assertTrue(containsCode(currencies, "USD"));
    }

    @Test
    void testIfUsdHasCorrectData() {
        Currency usd = findByCode(currencies, "USD");

        assertEquals(usd.getD(), 2);
        assertEquals(usd.getCode(), "USD");
        assertEquals(usd.getCurrency(), "United States dollar");
        assertEquals(usd.getNum(), 840);
    }

    @Test
    void testIfCurrencyWithNotesHasCorrectData() {
        Currency usd = findByCode(currencies, "COU");

        assertEquals(usd.getD(), 2);
        assertEquals(usd.getCode(), "COU");
        assertEquals(usd.getCurrency(), "Unidad de Valor Real (UVR) (funds code)");
        assertEquals(usd.getNum(), 970);
    }

    private static boolean containsCode(List<Currency> currencies, String code) {
        return currencies.stream()
                .anyMatch(currency -> currency.getCode().equals(code));
    }

    private static Currency findByCode(List<Currency> currencies, String code) {
        return currencies.stream()
                .filter(currency -> currency.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Currency not found: " + code));
    }
}