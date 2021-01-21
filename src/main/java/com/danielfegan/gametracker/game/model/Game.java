package com.danielfegan.gametracker.game.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Game {

    private String name;
    private Platform platform;
    private Genre genre;
    private LocalDate releaseDate;
    private int numberOfPlayers;
    private String publisher;

}
