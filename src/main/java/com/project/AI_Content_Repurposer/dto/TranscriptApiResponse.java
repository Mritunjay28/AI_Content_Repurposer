package com.project.AI_Content_Repurposer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptApiResponse {

    private List<TranscriptSegment> content;

    private String lang;
}