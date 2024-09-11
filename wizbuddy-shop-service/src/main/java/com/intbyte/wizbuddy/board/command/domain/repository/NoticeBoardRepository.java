package com.intbyte.wizbuddy.board.command.domain.repository;

import com.intbyte.wizbuddy.board.command.domain.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer> {
}
