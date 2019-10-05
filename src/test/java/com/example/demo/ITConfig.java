package com.example.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
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
    public MysqlDockerContainerWrapper mysqlDockerContainerWrapper() {
        return new MysqlDockerContainerWrapper();
    }

    @Bean
    public DataSource dataSource(PostgresDockerContainerWrapper postgresDockerContainerWrapper, MysqlDockerContainerWrapper mysqlDockerContainerWrapper) {
        PostgreSQLContainer pgDbContainer = postgresDockerContainerWrapper.getDbContainer();
        log.info("Will create PG Datasource: JdbcURL: {}, username: {}, pass: {}, driver class: {}", pgDbContainer.getJdbcUrl(), pgDbContainer.getUsername(), pgDbContainer.getPassword(), pgDbContainer.getDriverClassName());

        MySQLContainer mysqlDbContainer = mysqlDockerContainerWrapper.getDbContainer();
        log.info("Will create MySQL Datasource: JdbcURL: {}, username: {}, pass: {}, driver class: {}", mysqlDbContainer.getJdbcUrl(), mysqlDbContainer.getUsername(), mysqlDbContainer.getPassword(), mysqlDbContainer.getDriverClassName());

        HikariConfig dsConfig = new HikariConfig();

        dsConfig.setJdbcUrl(pgDbContainer.getJdbcUrl());
        dsConfig.setUsername(pgDbContainer.getUsername());
        dsConfig.setPassword(pgDbContainer.getPassword());
        dsConfig.setDriverClassName(pgDbContainer.getDriverClassName());

        return new HikariDataSource(dsConfig);
    }
}
