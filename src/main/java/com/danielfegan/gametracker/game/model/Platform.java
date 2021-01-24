package com.danielfegan.gametracker.game.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@AllArgsConstructor
public enum Platform {
    PS4("PS4"),
    PS5("PS5");

    private static final Map<String, Platform> NAME_MAP;

    static {
        NAME_MAP = Arrays.stream(values()).collect(Collectors.toMap(Platform::getValue, identity()));
    }

    private final String name;

    @JsonCreator
    public static Platform fromValue(final String value) {
        return NAME_MAP.get(value);
    }

    @JsonValue
    public String getValue() {
        return name;
    }
}
