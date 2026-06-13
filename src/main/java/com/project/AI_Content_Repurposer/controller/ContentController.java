package com.project.AI_Content_Repurposer.controller;

import com.project.AI_Content_Repurposer.dto.*;
import com.project.AI_Content_Repurposer.service.ContentGenerationService;
import com.project.AI_Content_Repurposer.service.TranscriptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    private final TranscriptService transcriptService;
    private final ContentGenerationService contentGenerationService;

    public ContentController(TranscriptService transcriptService, ContentGenerationService contentGenerationService) {
        this.transcriptService = transcriptService;
        this.contentGenerationService = contentGenerationService;
    }

    @GetMapping("/test")
    public String test() {
        return "Content module working";
    }

    @PostMapping("/transcript")
    public ResponseEntity<String> getTranscript(
            @RequestBody
            GenerateContentRequest request){

        return ResponseEntity.ok(
                transcriptService.getTranscript(
                        request.getYoutubeUrl()
                )
        );
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneratedContentResponse> generateContent( @RequestBody GenerateContentRequest request){

        return ResponseEntity.ok(
                contentGenerationService
                        .generateContent(
                                request.getYoutubeUrl()
                        )
        );
    }


    @GetMapping("/history")
    public ResponseEntity<List<ContentHistoryResponse>> getHistory() {

        return ResponseEntity.ok(
                contentGenerationService
                        .getHistory()
        );
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<ContentDetailsResponse>
    getHistoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                contentGenerationService
                        .getHistoryById(id)
        );
    }
}