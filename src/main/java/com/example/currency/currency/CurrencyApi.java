package com.example.currency.currency;

import com.example.currency.clients.CurrencyClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrencyApi{
    private CurrencyClient currencyClient;
    private String base;
    private String apiKey;

    public CurrencyApi(String url, String baseCurrency, String apiKey) {
         this.currencyClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(CurrencyClient .class, url);
         this.base = baseCurrency;
         this.apiKey = apiKey;
    }

    public Rates getLatestRates() {
        return this.currencyClient.getLatest(this.base, this.apiKey);
    }

    public Rates getRatesByDate(Date date) {
        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return this.currencyClient.getByDate(strDate, this.base, this.apiKey);
    }
}
