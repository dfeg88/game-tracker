package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.game.model.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.empty;

@RestController
public class GameController {

    @PostMapping
    public ResponseEntity<Mono<Void>> createGame(@RequestBody final Game game) {
        return ResponseEntity.ok(empty());
    }

}
