package com.example.demo.it;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

@Slf4j
@Getter
@Setter
public class DockerComposeConfig {
    @Value("${pg.urlTemplate}")
    private String pgJdbcUrlTemplate;
    @Value("${pg.username}")
    private String pgUsername;
    @Value("${pg.password}")
    private String pgPassword;
    @Value("${pg.dbName}")
    private String pgDbName;

    @PostConstruct
    void init() {
        log.info("On init");
    }

    public String toPgJdbcUrl(String host, int port) {
        return String.format(pgJdbcUrlTemplate, host, port);
    }
}
