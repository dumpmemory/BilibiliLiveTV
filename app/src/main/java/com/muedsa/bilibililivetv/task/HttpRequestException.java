package com.muedsa.bilibililivetv.task;

public class HttpRequestException extends RuntimeException {

    public HttpRequestException(String message) {
        super(message);
    }

    public static HttpRequestException create(String message) {
        return new HttpRequestException(message);
    }
}
