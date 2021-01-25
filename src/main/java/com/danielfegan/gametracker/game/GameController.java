package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.game.model.Game;
import com.danielfegan.gametracker.game.model.GameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Void> createGame(@RequestPart(name = "gameRequest") final GameRequest gameRequest,
                                           @RequestPart(name = "boxArt") final MultipartFile boxArt) {
        gameService.createGame(gameRequest, boxArt);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Game>> retrieveAll() {
        return ResponseEntity.ok(gameService.retrieveAll());
    }

}
