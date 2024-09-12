package com.intbyte.wizbuddy.comment.infrastructure.repository;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("findCommentAboutSubsBoard")
public interface SubsBoardRepository extends JpaRepository<SubsBoard, Integer> {

    SubsBoardDTO findBysubsCode(int subsCode);

    SubsBoard findBySubsCode(int subsCode);
}

