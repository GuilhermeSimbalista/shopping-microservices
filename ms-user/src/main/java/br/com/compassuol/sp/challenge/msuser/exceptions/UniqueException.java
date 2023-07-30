package br.com.compassuol.sp.challenge.msuser.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UniqueException extends RuntimeException{

    private String field;

    public UniqueException(String message, String field) {
        super(message);
        this.field = field;
    }
}
