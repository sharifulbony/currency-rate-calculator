package com.shariful.kn.task.exceptions;

public class HttpStatusException  extends RuntimeException{

    private final int statusCode;
    private final String responseBody;

    public HttpStatusException(int statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
