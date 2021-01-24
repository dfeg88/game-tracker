package com.danielfegan.gametracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseJson {
    private final String code;
}
