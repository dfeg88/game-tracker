package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.game.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Mono<Game> createGame(final Game game) {
        return gameRepository.insert(game);
    }

}
