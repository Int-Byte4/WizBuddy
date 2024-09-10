package com.intbyte.wizbuddy.task.command.domain.repository;

import com.intbyte.wizbuddy.task.command.domain.aggregate.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {


}
