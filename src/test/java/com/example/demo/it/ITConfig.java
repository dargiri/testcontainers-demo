package com.example.demo.it;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.Driver;
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
    public DockerComposeConfig dockerComposeConfig() {
        return new DockerComposeConfig();
    }

    @Bean
    public DockerComposeContainerWrapper dockerComposeContainerWrapper() {
        return new DockerComposeContainerWrapper();
    }

    @Bean
    public DataSource dataSource(DockerComposeContainerWrapper dockerComposeContainerWrapper,
                                 DockerComposeConfig dockerComposeConfig) {
        Pair<String, Integer> pgDbContainer = dockerComposeContainerWrapper.getPgDbContainer();

        HikariConfig dsConfig = new HikariConfig();

        dsConfig.setJdbcUrl(dockerComposeConfig.toPgJdbcUrl(pgDbContainer.getFirst(),pgDbContainer.getSecond()));
        dsConfig.setUsername(dockerComposeConfig.getPgUsername());
        dsConfig.setPassword(dockerComposeConfig.getPgPassword());
        dsConfig.setDriverClassName(Driver.class.getCanonicalName());

        return new HikariDataSource(dsConfig);
    }
}
