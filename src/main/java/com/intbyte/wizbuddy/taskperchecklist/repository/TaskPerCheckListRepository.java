package com.intbyte.wizbuddy.taskperchecklist.repository;

import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckList;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerChecklistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPerCheckListRepository extends JpaRepository<TaskPerCheckList, TaskPerChecklistId> {

}
