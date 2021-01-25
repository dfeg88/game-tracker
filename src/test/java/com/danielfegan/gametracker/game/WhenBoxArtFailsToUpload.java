package com.danielfegan.gametracker.game;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

public class WhenBoxArtFailsToUpload extends BaseTest {

    @MockBean
    private TransferManager transferManager;

    @BeforeAll
    public void setup() {
        Mockito.when(transferManager.upload(any(PutObjectRequest.class))).thenThrow(new RuntimeException("e"));
    }

    @Test
    public void returns_500_error() throws IOException {
        givenICallTheV1PostGameEndpoint(boxArt, 500);
    }



}
