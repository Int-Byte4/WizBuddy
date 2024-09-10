package com.intbyte.wizbuddy.like.command.domain.repository;

import com.intbyte.wizbuddy.like.command.domain.entity.ManualBoardLiked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualBoardLikedRepository extends JpaRepository<ManualBoardLiked, Integer> {
}
