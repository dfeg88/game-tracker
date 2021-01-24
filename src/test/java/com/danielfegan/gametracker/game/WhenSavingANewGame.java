package com.danielfegan.gametracker.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class WhenSavingANewGame extends BaseTest {

    @BeforeAll
    public void setup() throws IOException {
        giveICallTheV1PostGameEndpoint();
        andTheCallHasProcessedSuccessfully();
    }

    @Test
    public void saves_game_to_database() throws IOException {
        thenTheGameHasBeenSavedInTheDatabase();
    }

    @Test
    public void uploads_game_image_to_s3() throws IOException {
        andTheImageHasUploadedToS3();
    }

}
