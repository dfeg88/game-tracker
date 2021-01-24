package com.danielfegan.gametracker.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.danielfegan.gametracker.config.properties.S3ConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AWSConfiguration {

    private final S3ConfigurationProperties s3Properties;

    @Bean
    public AWSCredentials credentials() {
        return new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());
    }

    @Bean
    public AmazonS3 amazonS3Client(final AWSCredentials credentials) {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withClientConfiguration(new ClientConfiguration())
            .withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(s3Properties.getUrl(), s3Properties.getRegion())
            )
            .withChunkedEncodingDisabled(!s3Properties.isChunkedEncoding())
            .withPathStyleAccessEnabled(true)
            .build();
    }

    @Bean
    public boolean createBucket(final AmazonS3 amazonS3Client) {
        if (amazonS3Client.doesBucketExistV2(s3Properties.getBucketName())) {
            log.info("bucket {} already exists; will not create", s3Properties.getBucketName());
            return true;
        }
        log.info("creating bucket: {}", s3Properties.getBucketName());
        amazonS3Client.createBucket(s3Properties.getBucketName());
        return true;
    }

    @Bean
    public TransferManager transferManager(final AmazonS3 amazonS3Client) {
        return TransferManagerBuilder.standard().withS3Client(amazonS3Client).build();
    }

}
