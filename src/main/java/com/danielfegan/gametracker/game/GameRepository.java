package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.game.model.GameDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<GameDto, String> {
}
