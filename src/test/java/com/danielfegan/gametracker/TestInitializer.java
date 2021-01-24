package com.danielfegan.gametracker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Slf4j
public class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:0.11.2");
    private static final DockerImageName mongoImage = DockerImageName.parse("mongo:4.0.10");
    private static final int S3_PORT = 4566;
    private static final int MONGO_PORT = 27017;

    static final LocalStackContainer aws = new LocalStackContainer(localstackImage).withServices(S3)
        .withLogConsumer(new Slf4jLogConsumer(log));

    protected static final MongoDBContainer mongoDBContainer = new MongoDBContainer(mongoImage);

    static {
        aws.start();
        mongoDBContainer.start();
    }

    @Override
    public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
        final String s3Host = "http://" + aws.getContainerIpAddress() + ":" + aws.getMappedPort(S3_PORT);
        final Integer mongoPort = mongoDBContainer.getMappedPort(MONGO_PORT);

        log.info(String.format(
            "%n********************************************************%n"
                + "CONTAINERS STARTED WITH FOLLOWING DETAILS:%n"
                + "S3 HOST: %s%n"
                + "MONGO_DB HOST: localhost:%s%n"
                + "********************************************************",
            s3Host,
            mongoPort)
        );

        TestPropertyValues.of("s3.url=" + s3Host, "spring.data.mongodb.port=" + mongoPort)
            .applyTo(configurableApplicationContext.getEnvironment());
    }
}
