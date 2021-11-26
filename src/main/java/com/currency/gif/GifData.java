package com.currency.gif;

import java.util.Map;

public class GifData {
    private Map<String, Image> images;

    public GifData(Map<String, Image> images) {
        this.images = images;
    }

    public Image getImage(String size) {
        return images.get(size);
    }
}