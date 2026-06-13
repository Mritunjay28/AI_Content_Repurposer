package com.project.AI_Content_Repurposer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ContentDetailsResponse {

    private Long id;

    private String youtubeUrl;

    private String transcript;

    private String twitterThread;

    private String linkedinPost;

    private String blogSummary;

    private LocalDateTime createdAt;
}
