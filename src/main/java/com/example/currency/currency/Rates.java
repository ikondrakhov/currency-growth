package com.example.currency.currency;

import java.util.Map;

public class Rates {
    private Integer timestamp;
    private Map<String, Float> rates;

    public Integer getTimestamp() {
        return this.timestamp;
    }

    public Boolean containsCurrency(String currency) {
        return this.rates.containsKey(currency);
    }

    public Float getCurrencyValue(String currency) {
        return this.rates.get(currency);
    }
}
