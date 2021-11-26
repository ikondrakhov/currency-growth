package com.currency.services;

import com.currency.api.CurrencyApi;
import com.currency.currency.Rates;
import com.currency.exceptions.NoSuchCurrencyException;
import com.currency.api.GifApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class CurrencyGrowthService {
    @Autowired
    private CurrencyApi currencyApi;

    @Autowired
    private GifApi gifApi;

    public String getGifUrl(Float currencyDifference) throws NoSuchCurrencyException {
        String gifUrl;
        if(currencyDifference >= 0) {
            gifUrl = gifApi.getRichGif().getData().getImage("original").getWebpUrl();
        } else {
            gifUrl = gifApi.getBrokeGif().getData().getImage("original").getWebpUrl();
        }
        return gifUrl;
    }

    public Float getCurrencyDifference(String currencyCode) throws NoSuchCurrencyException {
        Rates todayRates = this.currencyApi.getLatestRates();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(todayRates.getTimestamp() * 1000));
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