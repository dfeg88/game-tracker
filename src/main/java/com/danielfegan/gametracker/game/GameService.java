package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.config.properties.S3ConfigurationProperties;
import com.danielfegan.gametracker.game.mapper.GameToDtoMapper;
import com.danielfegan.gametracker.game.model.GameDto;
import com.danielfegan.gametracker.game.model.GameRequest;
import com.danielfegan.gametracker.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public final class GameService {

    private final GameRepository gameRepository;
    private final S3Service s3Service;
    private final S3ConfigurationProperties s3ConfigurationProperties;

    public void createGame(final GameRequest gameRequest, final MultipartFile boxArt) throws IOException, InterruptedException {
        final GameDto gameDTO = GameToDtoMapper.INSTANCE.fromGameRequest(gameRequest);
        gameDTO.setId(UUID.randomUUID().toString());
        gameDTO.setBoxArtPath(s3ConfigurationProperties.getOutputPath() + "/" + boxArt.getOriginalFilename());
        gameRepository.save(gameDTO);
        s3Service.uploadFile(boxArt);
        log.info("hi");
    }



}
