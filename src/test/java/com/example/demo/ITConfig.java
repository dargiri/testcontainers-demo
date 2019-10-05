package com.example.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Slf4j
@TestConfiguration
public class ITConfig {

    @Bean
    public PostgresDockerContainerWrapper postgresDockerContainerWrapper() {
        return new PostgresDockerContainerWrapper();
    }

    @Bean
    public DataSource dataSource(PostgresDockerContainerWrapper postgresDockerContainerWrapper) {
        PostgreSQLContainer dbContainer = postgresDockerContainerWrapper.getDbContainer();

        log.info("Will create Datasource: JdbcURL: {}, username: {}, pass: {}, driver class: {}", dbContainer.getJdbcUrl(), dbContainer.getUsername(), dbContainer.getPassword(), dbContainer.getDriverClassName());

        HikariConfig dsConfig = new HikariConfig();

        dsConfig.setJdbcUrl(dbContainer.getJdbcUrl());
        dsConfig.setUsername(dbContainer.getUsername());
        dsConfig.setPassword(dbContainer.getPassword());
        dsConfig.setDriverClassName(dbContainer.getDriverClassName());

        return new HikariDataSource(dsConfig);
    }
}
