package me.bill.currency.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

class TestWikiCurrencyDocumentDownloader implements WikiCurrencyDocumentDownloader {

    private static final String CURRENCIES_HTML_FILENAME = "src/test/resources/wiki_currencies.html";

    @Override
    public Document loadDocument() throws IOException {
        return Jsoup.parse(new File(CURRENCIES_HTML_FILENAME), "UTF-8");
    }
}
