package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.game.model.GameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Void> createGame(@RequestPart(name = "gameRequest") final GameRequest gameRequest,
                                           @RequestPart(name = "boxArt") final MultipartFile boxArt) throws IOException, InterruptedException {
        gameService.createGame(gameRequest, boxArt);
        return ResponseEntity.ok().build();
    }

}
