package com.example.currency.clients;

import com.example.currency.currency.Rates;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "currency-client")
public interface CurrencyClient {

    @RequestLine("GET /api/latest.json?app_id={appId}&base={base}")
    Rates getLatest(@Param("base") String base,
                    @Param("appId") String appId);

    @RequestLine("GET /api/historical/{date}.json?app_id={appId}&base={base}")
    Rates getByDate(@Param("date") String date,
                    @Param("base") String base,
                    @Param("appId") String appId);
}