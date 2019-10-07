package com.example.demo.it;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.MySQLContainerProvider;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class MysqlDockerContainerWrapper {
    @Getter
    private final MySQLContainer dbContainer = new MySQLContainer("mysql:5.7.22");

    @PostConstruct
    void start() {
        log.info("Starting mysql Docker test container");
        dbContainer.start();
        log.info("Started mysql Docker test container");
    }

    @PreDestroy
    void stop() {
        log.info("Stopping mysql Docker test container");
        dbContainer.stop();
        log.info("Stopped mysql Docker test container");
    }
}
