package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.game.model.Game;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Configuration
public interface GameRepository extends ReactiveMongoRepository<Game, String> {

}
