package com.example.demo.it;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.testcontainers.containers.DockerComposeContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

@Slf4j
public class DockerComposeContainerWrapper {
    private static final int DEFAULT_MYSQL_PORT = 3306;
    private static final int DEFAULT_POSTGRESQL_PORT = 5432;
    private static final String POSTGRES_SERVICE = "postgres-it";
    private static final String MYSQL_SERVICE = "mysql-it";

    private final DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(new File("src/test/resources/docker-compose-it.yml"))
            .withExposedService(POSTGRES_SERVICE, DEFAULT_POSTGRESQL_PORT)
            .withExposedService(MYSQL_SERVICE, DEFAULT_MYSQL_PORT)
            ;

    @PostConstruct
    void start() {
        log.info("Starting Docker compose test container");
        dockerComposeContainer.start();
        log.info("Started Docker compose test container");
    }

    @PreDestroy
    void stop() {
        log.info("Stopping Docker compose test container");
        dockerComposeContainer.stop();
        log.info("Stopped Docker compose test container");
    }

    public Pair<String, Integer> getPgDbContainer() {
        return Pair.of(dockerComposeContainer.getServiceHost(POSTGRES_SERVICE, DEFAULT_POSTGRESQL_PORT),
                (dockerComposeContainer.getServicePort(POSTGRES_SERVICE, DEFAULT_POSTGRESQL_PORT)));
    }

    public Pair<String, Integer> getMysqlDbContainer() {
        return Pair.of(dockerComposeContainer.getServiceHost(MYSQL_SERVICE, DEFAULT_MYSQL_PORT),
                (dockerComposeContainer.getServicePort(MYSQL_SERVICE, DEFAULT_MYSQL_PORT)));
    }
}
