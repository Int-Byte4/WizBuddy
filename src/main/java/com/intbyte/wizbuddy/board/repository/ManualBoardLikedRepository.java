package com.intbyte.wizbuddy.board.repository;

import com.intbyte.wizbuddy.board.domain.entity.ManualBoardLiked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualBoardLikedRepository extends JpaRepository<ManualBoardLiked, Integer> {
}
