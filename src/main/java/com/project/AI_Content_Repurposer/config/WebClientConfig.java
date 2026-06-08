package com.project.AI_Content_Repurposer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder()
                .baseUrl(
                        "https://youtube-transcripts.p.rapidapi.com"
                );
    }
}