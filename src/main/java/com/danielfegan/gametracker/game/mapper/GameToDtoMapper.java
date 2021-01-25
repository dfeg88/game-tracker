package com.danielfegan.gametracker.game.mapper;

import com.danielfegan.gametracker.game.model.Game;
import com.danielfegan.gametracker.game.model.GameRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameToDtoMapper {
    GameToDtoMapper INSTANCE = Mappers.getMapper(GameToDtoMapper.class);

    Game fromGameRequest(GameRequest source);
}
