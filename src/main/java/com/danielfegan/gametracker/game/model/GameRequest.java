package com.danielfegan.gametracker.game.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GameRequest {

    private String name;
    private Platform platform;
    private Genre genre;
    private LocalDate releaseDate;
    private int numberOfPlayers;
    private String publisher;

}
