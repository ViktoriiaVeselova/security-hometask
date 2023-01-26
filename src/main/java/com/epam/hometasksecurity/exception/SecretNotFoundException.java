package com.epam.hometasksecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SecretNotFoundException extends RuntimeException{

    public SecretNotFoundException() {
        super("This secret wasn't found");
    }
}
