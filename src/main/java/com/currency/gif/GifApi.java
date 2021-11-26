package com.currency.gif;

import com.currency.clients.GifClient;
import feign.Feign;
import feign.gson.GsonDecoder;

public class GifApi {
    private GifClient gifClient;
    private String key;

    public GifApi(String url, String key) {
        this.gifClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GifClient.class, url);
        this.key = key;
    }

    public Gif getRichGif() {
        return this.gifClient.getRichGif(this.key);
    }

    public Gif getBrokeGif() {
        return this.gifClient.getBrokeGif(this.key);
    }
}
