package com.intbyte.wizbuddy.checklist.command.domain.service;

import com.intbyte.wizbuddy.checklist.command.domain.repository.CheckListRepository;
import com.intbyte.wizbuddy.checklist.command.infrastructure.service.InfraCheckListService;
import com.intbyte.wizbuddy.task.query.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DomainCheckListServiceImpl implements DomainCheckListService{

    private final CheckListRepository checkListRepository;
    private final InfraCheckListService infraCheckListService;

    @Autowired
    public DomainCheckListServiceImpl(CheckListRepository checkListRepository, InfraCheckListService infraCheckListService) {
        this.checkListRepository = checkListRepository;
        this.infraCheckListService = infraCheckListService;
    }

    @Override
    @Transactional
    public void validateAndAddTaskToCheckList(int checkListCode, int taskCode) {

        // 체크리스트의 ShopCode 조회
        Integer shopCodeByCheckListCode = checkListRepository.findById(checkListCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid checklist code"))
                .getShopCode();

        // Task의 ShopCode 조회
        TaskDTO taskById = infraCheckListService.findShopCodeByTaskCode(taskCode);
        Integer shopCodeByTaskCode = taskById.getShopCode();

        // 유효성 검사
        if (shopCodeByCheckListCode == null || shopCodeByTaskCode == null) {
            throw new IllegalArgumentException("Shop code is null");
        }

        if (shopCodeByCheckListCode == 0 || shopCodeByTaskCode == 0) {
            throw new IllegalArgumentException("Invalid shop code");
        }

        if (!shopCodeByCheckListCode.equals(shopCodeByTaskCode)) {
            throw new IllegalArgumentException("Shop codes do not match");
        }

        // 이미 존재하는 TaskPerCheckList인지 확인
        if (infraCheckListService.isTaskAlreadyInCheckList(checkListCode, taskCode)) {
            throw new IllegalArgumentException("Task is already in the checklist");
        }
    }
}
