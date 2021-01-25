package com.danielfegan.gametracker.game;

import com.danielfegan.gametracker.game.model.Game;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

import static java.nio.file.Files.readString;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenRetrievingGames extends BaseTest {

    @Value("classpath:json/expected-games-response.json")
    private Resource gamesList;

    private List<Game> actual;

    @BeforeAll
    public void setup() throws IOException {
        givenIHaveGamesSaved();
        andTheCallHasProcessedSuccessfully();
        actual = whenICallTheV1RetrieveAllEndpoint();
    }

    @Test
    public void returns_list_of_games_from_database() throws IOException {
        final List<Game> expected = objectMapper.readValue(readString(gamesList.getFile().toPath()), new TypeReference<>() {
        });

        assertThat(actual)
            .usingElementComparatorIgnoringFields("id")
            .containsExactlyInAnyOrderElementsOf(expected);
    }

}
