package com.intbyte.wizbuddy.task.query.service;

import com.intbyte.wizbuddy.task.query.dto.TaskQueryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TaskServiceTests {

    @Autowired
    private TaskService taskService;

    @Test
    public void id로_업무_1개_조회_테스트(){

        int taskCode = 1;

        assertNotNull(taskService.findTaskById(taskCode));
    }

    // 2-1번
    @Test
    public void 매장_id로_모든_업무_조회(){

        int shopCode = 1;
        List<TaskQueryDTO> taskQueryDTOS = taskService.findAllTaskByShopCode(shopCode);

        for (int i = 0; i < taskQueryDTOS.size(); i++) {
            assertEquals(shopCode, taskQueryDTOS.get(i).getShopCode());
        }
    }

    // 2-2번
    @Test
    public void 매장_id로_고정된_모든_업무_조회(){

        int shopCode = 1;
        List<TaskQueryDTO> fixedDTOs = taskService.findAllTaskByShopCodeByFixedState(shopCode);

        for (int i = 0; i < fixedDTOs.size(); i++) {
            assertEquals(true, fixedDTOs.get(i).isTaskFixedState());
        }
    }

    // 2-3번
    @Test
    public void 매장_id로_고정_안된_모든_업무_조회(){

        int shopCode = 1;
        List<TaskQueryDTO> nonFixedDTOs = taskService.findAllTaskByShopCodeByNonFixedState(shopCode);

        for (int i = 0; i < nonFixedDTOs.size(); i++) {
            assertEquals(false, nonFixedDTOs.get(i).isTaskFixedState());
        }
    }
}
