package com.wwi21sebgroup5.cinema.config;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TmdbConfig {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Bean
    public TmdbApi tmdbApi() {
        return new TmdbApi(apiKey);
    }

    @Bean
    public TmdbConfiguration tmdbConfig() {
        return tmdbApi().getConfiguration();
    }

}
