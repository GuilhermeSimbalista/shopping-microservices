package br.com.compassuol.sp.challenge.msauth.exception;

import br.com.compassuol.sp.challenge.msauth.exceptions.AuthAPIException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthAPIExceptionTest {

    @Test
    public void testAuthAPIExceptionWithStatusAndMessage() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Invalid request";

        AuthAPIException exception = new AuthAPIException(status, message);

        assertEquals(status, exception.getStatus());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testAuthAPIExceptionWithMessageAndStatusAndMessage() {
        HttpStatus status = HttpStatus.BAD_GATEWAY;
        String message = "Something went wrong";
        String customMessage = "Custom error message";

        AuthAPIException exception = new AuthAPIException(message, status, customMessage);

        assertEquals(status, exception.getStatus());
        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    public void testAuthAPIExceptionGetMessage() {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Resource not found";

        AuthAPIException exception = new AuthAPIException(status, message);

        assertEquals(message, exception.getMessage());
    }
}

