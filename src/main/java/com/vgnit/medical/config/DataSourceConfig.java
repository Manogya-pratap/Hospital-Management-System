package com.vgnit.medical.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Builds the DataSource from Render's DATABASE_URL when present.
 *
 * Render's managed PostgreSQL exposes a connection string in the form:
 *     postgres://user:password@host:5432/dbname
 * but the PostgreSQL JDBC driver / Spring requires:
 *     jdbc:postgresql://host:5432/dbname   (username & password supplied separately)
 *
 * This config detects a DATABASE_URL env var in the postgres://... form and
 * converts it. If DATABASE_URL is absent, Spring falls back to the normal
 * spring.datasource.* properties (used for local development), so this class
 * is safe in both environments.
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(Environment env) {
        String databaseUrl = env.getProperty("DATABASE_URL");

        // No Render-style URL -> let Spring use spring.datasource.* from properties.
        if (databaseUrl == null || databaseUrl.isBlank()) {
            return DataSourceBuilder.create()
                    .url(env.getProperty("spring.datasource.url"))
                    .username(env.getProperty("spring.datasource.username"))
                    .password(env.getProperty("spring.datasource.password"))
                    .driverClassName("org.postgresql.Driver")
                    .build();
        }

        try {
            URI uri = new URI(databaseUrl);
            String userInfo = uri.getUserInfo();               // "user:password"
            String username = userInfo.split(":")[0];
            String password = userInfo.contains(":") ? userInfo.split(":", 2)[1] : "";
            int port = uri.getPort() == -1 ? 5432 : uri.getPort();
            String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + port + uri.getPath();

            return DataSourceBuilder.create()
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();

        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid DATABASE_URL: " + databaseUrl, e);
        }
    }
}