package com.example.webcompany.entites;

import org.springframework.http.HttpStatus;

public class UserException {

    private HttpStatus code;
    private String message;

    public UserException(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpStatus getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "Exception status code: " + this.code + ", message: " + this.message;
    }
}
