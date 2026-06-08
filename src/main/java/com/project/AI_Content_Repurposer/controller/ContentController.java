package com.project.AI_Content_Repurposer.controller;

import com.project.AI_Content_Repurposer.dto.GenerateContentRequest;
import com.project.AI_Content_Repurposer.dto.GeneratedContentResponse;
import com.project.AI_Content_Repurposer.dto.TranscriptResponse;
import com.project.AI_Content_Repurposer.service.TranscriptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    private final TranscriptService transcriptService;

    public ContentController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @GetMapping("/test")
    public String test() {
        return "Content module working";
    }

    @PostMapping("/generate")
    public String generate(
            @Valid @RequestBody GenerateContentRequest request) {


        return transcriptService
                .getTranscript(request.getYoutubeUrl());
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
}