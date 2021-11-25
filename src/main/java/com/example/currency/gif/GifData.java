package com.example.currency.gif;

import java.util.Map;

public class GifData {
    private String embed_url;
    private Map<String, Image> images;

    public GifData(String embed_url, Map<String, Image> images) {
        this.embed_url = embed_url;
        this.images = images;
    }

    public Image getImage(String size) {
        return images.get(size);
    }
}