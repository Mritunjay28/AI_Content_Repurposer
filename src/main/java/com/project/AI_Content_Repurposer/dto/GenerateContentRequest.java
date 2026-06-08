package com.project.AI_Content_Repurposer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenerateContentRequest {

    @NotBlank
    private String youtubeUrl;
}