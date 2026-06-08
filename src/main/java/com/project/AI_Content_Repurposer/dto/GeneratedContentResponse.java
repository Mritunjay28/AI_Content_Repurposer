package com.project.AI_Content_Repurposer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratedContentResponse {

    private String transcript;

    private String twitterThread;

    private String linkedinPost;

    private String blogSummary;
}