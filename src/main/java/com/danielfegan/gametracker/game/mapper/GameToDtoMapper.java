package com.danielfegan.gametracker.game.mapper;

import com.danielfegan.gametracker.game.model.GameDto;
import com.danielfegan.gametracker.game.model.GameRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameToDtoMapper {
    GameToDtoMapper INSTANCE = Mappers.getMapper(GameToDtoMapper.class);

    GameDto fromGameRequest(GameRequest source);
}
