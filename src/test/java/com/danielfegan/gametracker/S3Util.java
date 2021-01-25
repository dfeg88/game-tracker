package com.danielfegan.gametracker;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.danielfegan.gametracker.config.properties.S3ConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

import static org.awaitility.Awaitility.await;

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

    public void deleteFiles() {
        final ListObjectsV2Result listObjectsV2Result = amazonS3Client.listObjectsV2(s3ConfigurationProperties.getBucketName());
        final DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(s3ConfigurationProperties.getBucketName())
            .withKeys(listObjectsV2Result.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .toArray(String[]::new)
            );

        if (listObjectsV2Result.getKeyCount() > 0) {
            amazonS3Client.deleteObjects(deleteObjectsRequest);
            await().atMost(Duration.ofSeconds(5)).until(this::isBucketEmpty);
        }
    }
}
