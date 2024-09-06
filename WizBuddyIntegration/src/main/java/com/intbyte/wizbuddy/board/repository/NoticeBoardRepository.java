package com.intbyte.wizbuddy.board.repository;

import com.intbyte.wizbuddy.board.domain.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer> {
}
