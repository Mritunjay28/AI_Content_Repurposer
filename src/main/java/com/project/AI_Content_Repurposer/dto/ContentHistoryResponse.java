package com.project.AI_Content_Repurposer.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ContentHistoryResponse {

    private Long id;

    private String youtubeUrl;

    private LocalDateTime createdAt;
}