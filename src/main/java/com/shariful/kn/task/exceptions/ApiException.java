package com.shariful.kn.task.exceptions;

public class ApiException extends HttpStatusException {
    public ApiException(int httpStatusCode, String responseBody) {
        super(httpStatusCode, responseBody);
    }
}
