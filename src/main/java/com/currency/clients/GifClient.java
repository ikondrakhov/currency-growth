package com.currency.clients;

import com.currency.gif.Gif;
import feign.Param;
import feign.RequestLine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="gif-client")
public interface GifClient {

    @RequestLine("GET /v1/gifs/random?tag=rich&api_key={appKey}")
    Gif getRichGif(@Param("appKey") String appKey);

    @RequestLine("GET /v1/gifs/random?tag=broke&api_key={appKey}")
    Gif getBrokeGif(@Param("appKey") String appKey);
}
