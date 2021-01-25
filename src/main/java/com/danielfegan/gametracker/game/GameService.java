package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.config.properties.S3ConfigurationProperties;
import com.danielfegan.gametracker.exception.GameTrackerException;
import com.danielfegan.gametracker.game.mapper.GameToDtoMapper;
import com.danielfegan.gametracker.game.model.Game;
import com.danielfegan.gametracker.game.model.GameRequest;
import com.danielfegan.gametracker.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public final class GameService {

    private final GameRepository gameRepository;
    private final S3Service s3Service;
    private final S3ConfigurationProperties s3ConfigurationProperties;

    public void createGame(final GameRequest gameRequest, final MultipartFile boxArt) {
        try {
            saveGameToDatabase(gameRequest, boxArt);
            s3Service.uploadFile(boxArt);
        } catch (final Exception e) {
            log.error("Error creating game: {}", e.getMessage(), e);
            throw new GameTrackerException("Failed to create game", INTERNAL_SERVER_ERROR);
        }
    }

    private void saveGameToDatabase(final GameRequest gameRequest, final MultipartFile boxArt) {
        final Game game = GameToDtoMapper.INSTANCE.fromGameRequest(gameRequest);
        game.setId(UUID.randomUUID().toString());
        game.setBoxArtPath(s3ConfigurationProperties.getOutputPath() + "/" + boxArt.getOriginalFilename());
        gameRepository.save(game);
    }

    public List<Game> retrieveAll() {
        return gameRepository.findAll();
    }
}
