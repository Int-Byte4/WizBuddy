package com.intbyte.wizbuddy.checklist.command.domain.service;

import com.intbyte.wizbuddy.checklist.command.domain.repository.CheckListRepository;
import com.intbyte.wizbuddy.checklist.command.infrastructure.service.InfraCheckListService;
import com.intbyte.wizbuddy.task.query.dto.TaskQueryDTO;
import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
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
        TaskQueryDTO taskById = infraCheckListService.findShopCodeByTaskCode(taskCode);
        Integer shopCodeByTaskCode = taskById.getShopCode();

        // 유효성 검사
        if (shopCodeByCheckListCode == null || shopCodeByTaskCode == null
            || shopCodeByCheckListCode == 0 || shopCodeByTaskCode == 0) {
            throw new CommonException(StatusEnum.SHOP_NOT_FOUND);
        }

        if (!shopCodeByCheckListCode.equals(shopCodeByTaskCode)) {
            throw new CommonException(StatusEnum.SHOP_IS_NOT_EQUAL);
        }

        // 이미 존재하는 TaskPerCheckList인지 확인
        if (infraCheckListService.isTaskAlreadyInCheckList(checkListCode, taskCode)) {
            throw new CommonException(StatusEnum.TASK_PER_CHECKLIST_DUPLICATE);
        }
    }
}
