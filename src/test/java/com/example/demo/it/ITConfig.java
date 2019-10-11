package com.example.demo.it;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Pair;

import javax.sql.DataSource;

@Slf4j
@TestConfiguration
@Profile("it")
public class ITConfig {

    @Bean
    public DockerComposeContainerWrapper dockerComposeContainerWrapper() {
        return new DockerComposeContainerWrapper();
    }

    @Bean
    public DataSource dataSource(DockerComposeContainerWrapper dockerComposeContainerWrapper,
                                 @Value("${pg.urlTemplate}") String pgUrlTemplate,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password
    ) {
        final Pair<String, Integer> pgDbContainer = dockerComposeContainerWrapper.getPgDbContainer();

        final String host = pgDbContainer.getFirst();
        final Integer port = pgDbContainer.getSecond();
        final String jdbcUrl = String.format(pgUrlTemplate, host, port);
        String postgresDriverClassName = Driver.class.getCanonicalName();

        log.info("Creating datasource for accessing {}", jdbcUrl);

        HikariConfig dsConfig = new HikariConfig();

        dsConfig.setJdbcUrl(jdbcUrl);
        dsConfig.setUsername(username);
        dsConfig.setPassword(password);
        dsConfig.setDriverClassName(postgresDriverClassName);

        return new HikariDataSource(dsConfig);
    }
}
