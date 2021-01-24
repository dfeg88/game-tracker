package com.danielfegan.gametracker.exception.handler;

import com.danielfegan.gametracker.exception.ErrorResponseJson;
import com.danielfegan.gametracker.exception.GameTrackerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(GameTrackerException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseJson> handleCountriesDataException(final GameTrackerException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponseJson(e.getMessage()));
    }

}
