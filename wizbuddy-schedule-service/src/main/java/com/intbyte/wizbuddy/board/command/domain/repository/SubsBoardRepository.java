package com.intbyte.wizbuddy.board.command.domain.repository;

import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("commandSubsBoardRepository")
public interface SubsBoardRepository extends JpaRepository<SubsBoard, Integer> {

}

