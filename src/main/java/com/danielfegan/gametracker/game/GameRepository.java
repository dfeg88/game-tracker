package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.game.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}
