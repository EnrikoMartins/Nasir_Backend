package com.copper.Nasir.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "tmdb")
public class TmdbConfig {

    private String apiKey;
    private String bearerToken;
    private String baseUrl;
    private String imageBaseUrl;

    // Getters e Setters
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getBearerToken() { return bearerToken; }
    public void setBearerToken(String bearerToken) { this.bearerToken = bearerToken; }

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public String getImageBaseUrl() { return imageBaseUrl; }
    public void setImageBaseUrl(String imageBaseUrl) { this.imageBaseUrl = imageBaseUrl; }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}