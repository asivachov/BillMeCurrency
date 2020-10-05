package me.bill.currency.service;

import org.jsoup.nodes.Document;

import java.io.IOException;

interface WikiCurrencyDocumentDownloader {

    Document loadDocument() throws IOException;

}
