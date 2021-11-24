package com.example.currency.controller;

import com.example.currency.currency.CurrencyApi;
import com.example.currency.exceptions.NoSuchCurrencyException;
import com.example.currency.currency.Rates;
import com.example.currency.clients.GifClient;
import com.example.currency.gif.Gif;
import com.example.currency.gif.GifApi;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;

@RestController
public class CurrencyController {

    @Autowired
    Environment env;

    private CurrencyApi currencyApi;
    private GifApi gifApi;

    @PostConstruct
    public void postConstruct() {
        this.currencyApi = new CurrencyApi(
                env.getProperty("exchange.rates.url"),
                env.getProperty("base.currency"),
                env.getProperty("CURRENCY_API_KEY"));
        this.gifApi = new GifApi(
                env.getProperty("giphy.api.url"),
                env.getProperty("GIF_API_KEY"));
    }

    @GetMapping("/currency-growth")
    @ResponseBody
    public String currency(
            @RequestParam(value="currencyCode", defaultValue = "USD")
                    String currencyCode
    ) {
        Float currencyDifference;
        try {
            currencyDifference = this.getCurrencyDifference(currencyCode);
        } catch(NoSuchCurrencyException e) {
            return e.getMessage();
        }

        String gifUrl;
        if(currencyDifference >= 0) {
            gifUrl = gifApi.getRichGif().getData().getImage("original").getWebpUrl();
        } else {
            gifUrl = gifApi.getBrokeGif().getData().getImage("original").getWebpUrl();
        }
        return "<img src='" + gifUrl + "'>";
    }

    private Float getCurrencyDifference(String currencyCode) throws NoSuchCurrencyException {
        Rates todayRates = this.currencyApi.getLatestRates();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long)todayRates.getTimestamp() * 1000));
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        Rates yesterdayRates = currencyApi.getRatesByDate(calendar.getTime());

        if(!todayRates.containsCurrency(currencyCode)) {
            throw new NoSuchCurrencyException("Currency " + currencyCode + " does not exist.");
        }

        Float currencyToday = todayRates.getCurrencyValue(currencyCode);
        Float currencyYesterday = yesterdayRates.getCurrencyValue(currencyCode);

        return currencyToday - currencyYesterday;
    }
}
