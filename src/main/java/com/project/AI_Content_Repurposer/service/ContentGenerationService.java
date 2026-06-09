package com.project.AI_Content_Repurposer.service;

import com.project.AI_Content_Repurposer.dto.GeneratedContentResponse;
import com.project.AI_Content_Repurposer.entity.ContentHistory;
import com.project.AI_Content_Repurposer.entity.User;
import com.project.AI_Content_Repurposer.repository.ContentHistoryRepository;
import com.project.AI_Content_Repurposer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentGenerationService {

    private final TranscriptService transcriptService;
    private final GeminiService geminiService;
    private final ContentHistoryRepository contentHistoryRepository;
    private final UserRepository userRepository;

    public GeneratedContentResponse generateContent(
            String youtubeUrl){

        String transcript =
                transcriptService.getTranscript(youtubeUrl);

        GeneratedContentResponse generatedContent =
                geminiService.generateContent(transcript);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        ContentHistory history =
                new ContentHistory();

        history.setYoutubeUrl(youtubeUrl);
        history.setTranscript(transcript);

        history.setTwitterThread(
                generatedContent.getTwitterThread()
        );

        history.setLinkedinPost(
                generatedContent.getLinkedinPost()
        );

        history.setBlogSummary(
                generatedContent.getBlogSummary()
        );

        history.setUser(user);

        contentHistoryRepository.save(history);

        return generatedContent;
    }
}