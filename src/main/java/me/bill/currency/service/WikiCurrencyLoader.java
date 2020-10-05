package me.bill.currency.service;

import me.bill.currency.domain.Currency;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
class WikiCurrencyLoader {

    private static final String CURRENCY_TABLE_CAPTION_TEXT_NEEDLE = "Active ISO 4217 currency codes";

    private static final int CODE_COLUMN_INDEX = 0;
    private static final int NUM_COLUMN_INDEX = 1;
    private static final int D_COLUMN_INDEX = 2;
    private static final int CURRENCY_COLUMN_INDEX = 3;

    private static final String NOTE_NEEDLE = "[";

    private final WikiCurrencyDocumentDownloader currencyDocumentDownloader;

    @Autowired
    public WikiCurrencyLoader(WikiCurrencyDocumentDownloader currencyDocumentDownloader) {
        this.currencyDocumentDownloader = currencyDocumentDownloader;
    }

    public List<Currency> load() throws IOException {
        Document wikiPage = currencyDocumentDownloader.loadDocument();

        Element currencyTable = getCurrencyTable(wikiPage);
        Elements rows = getRows(currencyTable);

        return extractDataFromRows(rows);
    }

    private Element getCurrencyTable(Document wikiPage) {
        Element currencyTable = wikiPage
                .select(String.format("caption:contains(%s)", CURRENCY_TABLE_CAPTION_TEXT_NEEDLE))
                .first();

        return currencyTable.parent();
    }

    private Elements getRows(Element currencyTable) {
        Element tbody = currencyTable.children().get(1);

        return tbody.children();
    }

    private List<Currency> extractDataFromRows(Elements rows) {
        List<Currency> currencies = new ArrayList<>();

        Iterator<Element> rowIter = rows.iterator();
        rowIter.next();//  skip header row

        while (rowIter.hasNext()) {
            Element row = rowIter.next();

            currencies.add(extractData(row));
        }

        return currencies;
    }

    private Currency extractData(Element row) {
        Currency currency = new Currency();

        currency.setCode(extractCode(row));
        currency.setNum(extractNum(row));
        currency.setD(extractD(row));
        currency.setCurrency(extractCurrency(row));

        return currency;
    }

    private String extractCode(Element row) {
        String code = row.children().get(CODE_COLUMN_INDEX).text();
        return removeNotes(code);
    }

    private Integer extractNum(Element row) {
        String num = row.children().get(NUM_COLUMN_INDEX).text();
        return Integer.valueOf(removeNotes(num));
    }

    private Integer extractD(Element row) {
        String d = row.children().get(D_COLUMN_INDEX).text();
        d = removeNotes(d);

        if (d.equals(".")) {
            return null;
        } else {
            return Integer.valueOf(d);
        }
    }

    private String extractCurrency(Element row) {
        String currency = row.children().get(CURRENCY_COLUMN_INDEX).text();
        return removeNotes(currency);
    }

    //  removes notes, f.e. "Russian ruble[1]" -> "Russian ruble"
    private static String removeNotes(String value) {
        int noteStartIndex = value.indexOf(NOTE_NEEDLE);

        if (noteStartIndex >= 0) {
            value = value.substring(0, noteStartIndex);
        }

        return value;
    }

}
