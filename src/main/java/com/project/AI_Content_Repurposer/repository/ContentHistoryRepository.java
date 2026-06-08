package com.project.AI_Content_Repurposer.repository;

import com.project.AI_Content_Repurposer.entity.ContentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentHistoryRepository
        extends JpaRepository<ContentHistory, Long> {

    List<ContentHistory> findByUserUsername(String username);
}