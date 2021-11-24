package com.example.currency.gif;

import java.util.Map;

public class GifData {
    private String embed_url;
    private Map<String, Image> images;

    public Image getImage(String size) {
        return images.get(size);
    }
}