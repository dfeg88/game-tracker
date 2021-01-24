package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.S3Util;
import com.danielfegan.gametracker.TestInitializer;
import com.danielfegan.gametracker.game.model.GameDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import static io.restassured.RestAssured.given;
import static java.nio.file.Files.readString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(initializers = TestInitializer.class)
public class BaseTest {

    @Value("classpath:image/cartman.jpg")
    private Resource boxArt;

    @Value("classpath:json/post.json")
    private Resource gameRequest;

    @Value("classpath:json/expected-game.json")
    private Resource expectedGameInDatabase;

    @LocalServerPort
    protected int port;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private S3Util s3Util;

    @Autowired
    private GameRepository gameRepository;

    protected void giveICallTheV1PostGameEndpoint() throws IOException {
        given()
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .accept(ContentType.ANY)
            .multiPart(new MultiPartSpecBuilder(boxArt.getFile())
                .fileName(boxArt.getFilename())
                .controlName("boxArt")
                .mimeType("image/jpeg")
                .build()
            )
            .multiPart(new MultiPartSpecBuilder(readString(gameRequest.getFile().toPath()))
                .controlName("gameRequest")
                .mimeType("application/json")
                .build()
            )
            .post("http://localhost:" + port + "/v1/game")
            .then()
            .statusCode(200);
    }

    protected void andTheCallHasProcessedSuccessfully() {
        await().atMost(Duration.ofSeconds(5)).until(() ->
            gameRepository.count() > 0 && !s3Util.isBucketEmpty()
        );

    }

    protected void thenTheGameHasBeenSavedInTheDatabase() throws IOException {
        final GameDto actual = gameRepository.findAll().get(0);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(objectMapper.readValue(readString(expectedGameInDatabase.getFile().toPath()), GameDto.class));
    }

    protected void andTheImageHasUploadedToS3() throws IOException {
        final GameDto gameDto = gameRepository.findAll().get(0);
        final byte[] actual = s3Util.getImage(gameDto.getBoxArtPath());
        final byte[] expected = Files.readAllBytes(boxArt.getFile().toPath());

        assertThat(actual).isEqualTo(expected);
    }

}
