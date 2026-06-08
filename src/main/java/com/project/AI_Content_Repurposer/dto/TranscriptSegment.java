package com.project.AI_Content_Repurposer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptSegment {

    private String text;

    private Double duration;

    private Double offset;

    private String lang;
}