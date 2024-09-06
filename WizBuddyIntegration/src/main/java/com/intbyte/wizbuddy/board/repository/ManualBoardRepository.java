package com.intbyte.wizbuddy.board.repository;

import com.intbyte.wizbuddy.board.domain.entity.ManualBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualBoardRepository extends JpaRepository<ManualBoard, Integer> {
}
