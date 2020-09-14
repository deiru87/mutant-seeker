package com.mercadolibre.mutant.consolidator.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Class that configures access to Databases
 *
 * @Author: Deimer Ballesteros
 */
@Configuration
@EnableR2dbcRepositories
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Value("${database.host}")
    private String host;
    @Value("${database.port}")
    private int port;
    @Value("${database.service}")
    private String database;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    @Override
    @Bean("connectionConsolidator")
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .database(database)
                        .username(username)
                        .password(password)
                        .build());
    }

}