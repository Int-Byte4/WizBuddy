package com.intbyte.wizbuddy.like.repository;

import com.intbyte.wizbuddy.like.domain.entity.NoticeBoardLiked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardLikedRepository extends JpaRepository<NoticeBoardLiked, Integer> {
}
