package br.com.compassuol.sp.challenge.msauth.exceptions;

import org.springframework.http.HttpStatus;

public class AuthAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public AuthAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AuthAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
