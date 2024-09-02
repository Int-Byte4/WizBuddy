package com.intbyte.wizbuddy.taskperchecklist.repository;

import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import com.intbyte.wizbuddy.task.domain.entity.Task;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerCheckList;
import com.intbyte.wizbuddy.taskperchecklist.domain.entity.TaskPerChecklistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskPerCheckListRepository extends JpaRepository<TaskPerCheckList, TaskPerChecklistId> {

    // task를 통한 삭제 JPQL
    @Modifying
    @Transactional
    @Query("DELETE FROM TaskPerCheckList t WHERE t.task = :task")
    void deleteByTask(@Param("task") Task task);

    // checkList를 통한 삭제 JPQL
    @Modifying
    @Transactional
    @Query("DELETE FROM TaskPerCheckList t WHERE t.checkList = :checkList")
    void deleteByCheckList(@Param("checkList") CheckList checkList);

}
