package com.project.AI_Content_Repurposer.service;

import com.project.AI_Content_Repurposer.dto.ContentDetailsResponse;
import com.project.AI_Content_Repurposer.dto.ContentHistoryResponse;
import com.project.AI_Content_Repurposer.dto.GeneratedContentResponse;
import com.project.AI_Content_Repurposer.entity.ContentHistory;
import com.project.AI_Content_Repurposer.entity.User;
import com.project.AI_Content_Repurposer.repository.ContentHistoryRepository;
import com.project.AI_Content_Repurposer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentGenerationService {

    private final TranscriptService transcriptService;
    private final GeminiService geminiService;
    private final ContentHistoryRepository contentHistoryRepository;
    private final UserRepository userRepository;

    @Transactional
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

    public List<ContentHistoryResponse> getHistory() {

        String username =
                Objects.requireNonNull(SecurityContextHolder
                                .getContext()
                                .getAuthentication())
                        .getName();

        List<ContentHistory> histories =
                contentHistoryRepository
                        .findByUserUsername(username);

        return histories.stream()
                .map(history ->
                        new ContentHistoryResponse(
                                history.getId(),
                                history.getYoutubeUrl(),
                                history.getCreatedAt()
                        )
                )
                .toList();
    }

    public ContentDetailsResponse getHistoryById(
            Long id) {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        ContentHistory history =
                contentHistoryRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Content not found"
                                )
                        );

        // check if asked history belongs to the same user who asked for history
        if (!history.getUser()
                .getUsername()
                .equals(username)) {

            throw new RuntimeException(
                    "Access denied"
            );
        }

        return new ContentDetailsResponse(
                history.getId(),
                history.getYoutubeUrl(),
                history.getTranscript(),
                history.getTwitterThread(),
                history.getLinkedinPost(),
                history.getBlogSummary(),
                history.getCreatedAt()
        );
    }

    @Transactional
    public void deleteHistory(Long id) {
        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        ContentHistory history =
                contentHistoryRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Content not found"
                                )
                        );

        // check if history belongs to the same user
        if (!history.getUser()
                .getUsername()
                .equals(username)) {

            throw new RuntimeException(
                    "Access denied"
            );
        }

        contentHistoryRepository.delete(history);
    }
}