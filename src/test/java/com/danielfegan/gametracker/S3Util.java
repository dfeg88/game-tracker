package com.danielfegan.gametracker;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.danielfegan.gametracker.config.properties.S3ConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Util {

    private final AmazonS3 amazonS3Client;
    private final S3ConfigurationProperties s3ConfigurationProperties;

    public boolean isBucketEmpty() {
        return amazonS3Client.listObjectsV2(s3ConfigurationProperties.getBucketName()).getKeyCount() == 0;
    }

    public byte[] getImage(final String key) throws IOException {
        final S3Object object = amazonS3Client.getObject(
            new GetObjectRequest(
                s3ConfigurationProperties.getBucketName(),
                key
            )
        );

        return object.getObjectContent().readAllBytes();
    }

}
