package com.danielfegan.gametracker.game.mapper;

import com.danielfegan.gametracker.game.model.GameDto;
import com.danielfegan.gametracker.game.model.GameRequest;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-24T13:48:52+0000",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.9.1 (AdoptOpenJDK)"
)
public class GameToDtoMapperImpl implements GameToDtoMapper {

    @Override
    public GameDto fromGameRequest(GameRequest source) {
        if ( source == null ) {
            return null;
        }

        GameDto gameDto = new GameDto();

        gameDto.setName( source.getName() );
        gameDto.setPlatform( source.getPlatform() );
        gameDto.setGenre( source.getGenre() );
        gameDto.setReleaseDate( source.getReleaseDate() );
        gameDto.setNumberOfPlayers( source.getNumberOfPlayers() );
        gameDto.setPublisher( source.getPublisher() );

        return gameDto;
    }
}
