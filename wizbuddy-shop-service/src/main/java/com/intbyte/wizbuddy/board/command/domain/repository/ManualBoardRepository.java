package com.intbyte.wizbuddy.board.command.domain.repository;

import com.intbyte.wizbuddy.board.command.domain.entity.ManualBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualBoardRepository extends JpaRepository<ManualBoard, Integer> {
}
