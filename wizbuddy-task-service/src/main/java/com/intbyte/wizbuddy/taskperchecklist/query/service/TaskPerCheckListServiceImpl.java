package com.intbyte.wizbuddy.taskperchecklist.query.service;

import com.intbyte.wizbuddy.taskperchecklist.query.dto.TaskPerCheckListQueryDTO;
import com.intbyte.wizbuddy.taskperchecklist.query.repository.TaskPerCheckListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskPerCheckListServiceImpl implements TaskPerCheckListService {

    private final TaskPerCheckListMapper taskPerCheckListMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskPerCheckListServiceImpl(TaskPerCheckListMapper taskPerCheckListMapper, ModelMapper modelMapper) {
        this.taskPerCheckListMapper = taskPerCheckListMapper;
        this.modelMapper = modelMapper;
    }

    // 1-1. 특정 체크리스트에 존재하는 모든 완료된 업무 조회
    @Override
    @Transactional
    public List<TaskPerCheckListQueryDTO> findAllTaskPerCheckListByCheckListCodeByFinished(int checkListCode){

        return taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCodeByFinished(checkListCode);
    }

    // 1-2. 특정 체크리스트에 존재하는 모든 미완료된 업무 조회
    @Override
    @Transactional
    public List<TaskPerCheckListQueryDTO> findAllTaskPerCheckListByCheckListCodeByNotFinished(int checkListCode) {

        return taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCodeByNonFinished(checkListCode);
    }

    // 1-3. 특정 체크리스트에 존재하는 모든 업무 조회(완료 + 미완료)
    @Override
    @Transactional
    public List<TaskPerCheckListQueryDTO> findAllTaskPerCheckListByCheckListCode(int checkListCode){

        return taskPerCheckListMapper.findAllTaskPerCheckListByCheckListCode(checkListCode);
    }
}
