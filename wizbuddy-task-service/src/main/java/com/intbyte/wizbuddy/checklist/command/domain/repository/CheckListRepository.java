package com.intbyte.wizbuddy.checklist.command.domain.repository;

import com.intbyte.wizbuddy.checklist.command.domain.aggregate.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Integer> {
}
