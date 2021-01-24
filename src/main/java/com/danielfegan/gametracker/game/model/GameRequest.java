package com.danielfegan.gametracker.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameRequest {

    private String name;
    private Platform platform;
    private Genre genre;
    private LocalDate releaseDate;
    private int numberOfPlayers;
    private String publisher;
    private MultipartFile boxArt;

}
