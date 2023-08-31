package com.example.board2.repository;

import com.example.board2.entity.PopularSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopularSearchRepository extends JpaRepository<PopularSearchEntity, String> {
    public List<PopularSearchEntity> findTop10ByOrderByPopularSearchCountDesc();
}
