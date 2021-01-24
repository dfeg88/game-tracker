package com.danielfegan.gametracker.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "games")
public class GameDto {
    @Id
    private String id;
    private String name;
    private Platform platform;
    private Genre genre;
    private LocalDate releaseDate;
    private int numberOfPlayers;
    private String publisher;
    private String boxArtPath;
}
