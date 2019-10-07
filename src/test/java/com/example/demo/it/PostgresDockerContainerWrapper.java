package com.example.demo.it;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class PostgresDockerContainerWrapper {
    @Getter
    private final PostgreSQLContainer dbContainer = new PostgreSQLContainer("postgres:9.5.4");

    @PostConstruct
    void start() {
        log.info("Starting postgres Docker test container");
        dbContainer.start();
        log.info("Started postgres Docker test container");
    }

    @PreDestroy
    void stop() {
        log.info("Stopping postgres Docker test container");
        dbContainer.stop();
        log.info("Stopped postgres Docker test container");
    }
}
