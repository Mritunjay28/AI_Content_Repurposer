package com.project.AI_Content_Repurposer.service;

import com.project.AI_Content_Repurposer.dto.TranscriptApiResponse;
import com.project.AI_Content_Repurposer.dto.TranscriptSegment;
import com.project.AI_Content_Repurposer.utility.YoutubeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranscriptService {

    private final YoutubeUtil youtubeUtil;

    private final WebClient.Builder webClientBuilder;

    @Value("${transcript.api.key}")
    private String apiKey;

    @Value("${transcript.api.host}")
    private String apiHost;

    public String getTranscript(String youtubeUrl) {

        String videoId =
                youtubeUtil.extractVideoId(youtubeUrl);

        TranscriptApiResponse response =
                fetchTranscript(
                        youtubeUrl,
                        videoId
                );

        return mergeTranscript(response);
    }

    private String mergeTranscript(
            TranscriptApiResponse response) {

        return response.getContent()
                .stream()
                .map(
                        TranscriptSegment::getText
                )
                .collect(
                        Collectors.joining(" ")
                );
    }

    private TranscriptApiResponse fetchTranscript(
            String youtubeUrl,
            String videoId) {

        return webClientBuilder.build()
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/youtube/transcript")
                                .queryParam("url", youtubeUrl)
                                .queryParam("videoId", videoId)
                                .queryParam("chunkSize", 500)
                                .queryParam("text", false)
                                .queryParam("lang", "en")
                                .build())
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .retrieve()
                .bodyToMono(
                        TranscriptApiResponse.class
                )
                .block();
    }

}