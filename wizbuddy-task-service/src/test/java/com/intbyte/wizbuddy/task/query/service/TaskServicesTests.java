package com.intbyte.wizbuddy.task.query.service;

import com.intbyte.wizbuddy.task.query.dto.TaskQueryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class TaskServicesTests {

    @Autowired
    private TaskService taskService;

    @Test
    @Transactional // 1번
    public void id로_업무_1개_조회_테스트(){

        int taskCode = 1;

        TaskQueryDTO taskById = taskService.findTaskById(taskCode);
        System.out.println("taskById = " + taskById);
    }

    // 2-1번
    @Test
    @Transactional
    public void 매장_id로_모든_업무_조회(){

        int shopCode = 1;

        List<TaskQueryDTO> allTaskByShopCode = taskService.findAllTaskByShopCode(shopCode);
        System.out.println(allTaskByShopCode.toString());
    }

    // 2-2번
    @Test
    @Transactional
    public void 매장_id로_고정된_모든_업무_조회(){

        int shopCode = 1;
        List<TaskQueryDTO> allTask = taskService.findAllTaskByShopCodeByFixedState(shopCode);
        System.out.println(allTask.toString());
    }

    // 2-3번
    @Test
    @Transactional
    public void 매장_id로_고정_안된_모든_업무_조회(){

        int shopCode = 1;
        List<TaskQueryDTO> allTask = taskService.findAllTaskByShopCodeByNonFixedState(shopCode);
        System.out.println(allTask.toString());
    }
}
