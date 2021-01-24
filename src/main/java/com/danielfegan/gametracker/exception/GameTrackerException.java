package com.danielfegan.gametracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GameTrackerException extends RuntimeException {

    private final HttpStatus status;

    public GameTrackerException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

}
