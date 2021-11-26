package com.currency.currency;

import java.util.Map;

public class Rates {
    private Long timestamp;
    private Map<String, Float> rates;

    public Rates(Long timestamp, Map<String, Float> rates) {
        this.timestamp = timestamp;
        this.rates = rates;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public Boolean containsCurrency(String currency) {
        return this.rates.containsKey(currency);
    }

    public Float getCurrencyValue(String currency) {
        return this.rates.get(currency);
    }
}
