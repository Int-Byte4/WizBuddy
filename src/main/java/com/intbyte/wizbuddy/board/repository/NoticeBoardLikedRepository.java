package com.intbyte.wizbuddy.board.repository;

import com.intbyte.wizbuddy.board.domain.entity.NoticeBoardLiked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardLikedRepository extends JpaRepository<NoticeBoardLiked, Integer> {
}
