package com.project.AI_Content_Repurposer.utility;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class YoutubeUtil {

    public String extractVideoId(String youtubeUrl) {

        String regex =
                "(?:v=|\\/)([0-9A-Za-z_-]{11})";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(youtubeUrl);

        if(matcher.find()) {
            return matcher.group(1);
        }

        throw new RuntimeException(
                "Invalid YouTube URL"
        );
    }
}