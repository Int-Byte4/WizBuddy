package com.intbyte.wizbuddy.board.repository;

import com.intbyte.wizbuddy.board.domain.entity.SubsBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsBoardRepository extends JpaRepository<SubsBoard, Integer> {

}
