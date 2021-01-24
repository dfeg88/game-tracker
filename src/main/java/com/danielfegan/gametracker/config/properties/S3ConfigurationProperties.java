package com.danielfegan.gametracker.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "s3")
@Data
public class S3ConfigurationProperties {
    private String url;
    private String region;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private boolean iamRole;
    private boolean chunkedEncoding;
    private String outputPath;
}
