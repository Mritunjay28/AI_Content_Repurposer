package com.project.AI_Content_Repurposer.service;

import com.project.AI_Content_Repurposer.dto.GeminiResponse;
import com.project.AI_Content_Repurposer.dto.GeneratedContentResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final WebClient.Builder webClientBuilder;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeneratedContentResponse generateContent(String transcript) {

        String prompt = buildPrompt(transcript);

        Map<String, Object> requestBody = Map.of(
                "contents",
                List.of(
                        Map.of(
                                "parts",
                                List.of(
                                        Map.of(
                                                "text",
                                                prompt
                                        )
                                )
                        )
                )
        );

        GeminiResponse response =
                webClientBuilder
                        .build()
                        .post()
                        .uri(
                                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                                        + apiKey
                        )
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(GeminiResponse.class)
                        .block();


        String geminiText =
                response.getCandidates()
                        .getFirst()
                        .getContent()
                        .getParts()
                        .getFirst()
                        .getText();


        geminiText = geminiText
                .replace("```json", "")
                .replace("```", "")
                .trim();

        System.out.println(geminiText);

        ObjectMapper objectMapper =
                new ObjectMapper();


        try {

            GeneratedContentResponse result =
                    objectMapper.readValue(
                            geminiText,
                            GeneratedContentResponse.class
                    );

            result.setTranscript(transcript);

            return result;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse Gemini response",
                    e
            );
        }
    }

    private String buildPrompt(String transcript) {

        return """
        Return ONLY valid JSON.
        Convert the following transcript into:
        1. A Twitter Thread about 100 words max
        2. A LinkedIn Post - 250 max
        3. A Blog Summary - 300 max

        {
          "twitterThread":"",
          "linkedinPost":"",
          "blogSummary":""
        }

        Transcript:

        %s
        """.formatted(transcript);
    }
}
