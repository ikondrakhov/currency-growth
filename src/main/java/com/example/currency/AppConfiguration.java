package com.example.currency;

import com.example.currency.currency.CurrencyApi;
import com.example.currency.gif.GifApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfiguration {
    @Autowired
    Environment env;

    @Bean
    public CurrencyApi currencyApi() {
        return new CurrencyApi(
                env.getProperty("exchange.rates.url"),
                env.getProperty("base.currency"),
                env.getProperty("CURRENCY_API_KEY"));
    }

    @Bean
    public GifApi gifApi() {
        return new GifApi(
                env.getProperty("giphy.api.url"),
                env.getProperty("GIF_API_KEY"));
    }
}
