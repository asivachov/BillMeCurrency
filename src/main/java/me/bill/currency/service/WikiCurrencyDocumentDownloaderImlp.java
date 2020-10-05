package me.bill.currency.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class WikiCurrencyDocumentDownloaderImlp implements WikiCurrencyDocumentDownloader {

    private static final String WIKI_URL = "https://en.wikipedia.org/wiki/ISO_4217";

    @Override
    public Document loadDocument() throws IOException {
        return Jsoup.connect(WIKI_URL).get();
    }
}
