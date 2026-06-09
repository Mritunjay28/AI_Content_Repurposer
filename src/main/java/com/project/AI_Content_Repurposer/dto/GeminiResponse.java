package com.project.AI_Content_Repurposer.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeminiResponse {
    private List<GeminiCandidate> candidates;
}