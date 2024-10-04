package com.intbyte.wizbuddy.like.command.domain.repository;

import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManualBoardLikedRepository extends JpaRepository<ManualBoardLiked, Integer> {
    List<ManualBoardLiked> findLikesByManualCode(int manualCode);
}
