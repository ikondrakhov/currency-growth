package com.currency.gif;

public class Gif {
    private GifData data;

    public Gif(GifData data) {
        this.data = data;
    }

    public GifData getData() {
        return data;
    }

    public String getImageUrl(String size) {
        return this.data.getImage(size).getWebpUrl();
    }
}
