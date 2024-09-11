package com.intbyte.wizbuddy.checklist.command.application.service;

import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;
import com.intbyte.wizbuddy.checklist.command.domain.aggregate.entity.CheckList;
import com.intbyte.wizbuddy.checklist.command.domain.repository.CheckListRepository;
import com.intbyte.wizbuddy.checklist.command.domain.service.DomainCheckListService;
import com.intbyte.wizbuddy.checklist.command.infrastructure.client.ShopServiceClient;
import com.intbyte.wizbuddy.checklist.command.infrastructure.service.InfraCheckListServiceImpl;
import com.intbyte.wizbuddy.checklist.query.dto.CheckListQueryDTO;
import com.intbyte.wizbuddy.checklist.query.service.CheckListService;
import com.intbyte.wizbuddy.task.query.dto.TaskDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AppCheckListServiceImpl implements AppCheckListService {

    private final CheckListRepository checkListRepository;
    private final ModelMapper modelMapper;
    private final InfraCheckListServiceImpl infraCheckListService;
    private final ShopServiceClient shopServiceClient;
    private final DomainCheckListService domainCheckListService;

    @Autowired
    public AppCheckListServiceImpl(CheckListRepository checkListRepository, ModelMapper modelMapper, InfraCheckListServiceImpl infraCheckListService, ShopServiceClient shopServiceClient, CheckListService checkListService, DomainCheckListService domainCheckListService) {
        this.checkListRepository = checkListRepository;
        this.modelMapper = modelMapper;
        this.infraCheckListService = infraCheckListService;
        this.shopServiceClient = shopServiceClient;
        this.domainCheckListService = domainCheckListService;
    }

    // command 1. 특정 매장의 체크리스트 최초 생성 후, 고정된 업무를 추가 -> infra에서 진행
    @Override
    @Transactional
    public void insertCheckList(CheckListDTO checkListDTO) {

        CheckList checkList = CheckList.builder()
                .checkListTitle(checkListDTO.getCheckListTitle())
                .checkListFlag(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shopCode(checkListDTO.getShopCode())
                .build();

        try{
            checkListRepository.save(checkList);
            infraCheckListService.insertFixedTaskAndTaskPerCheckList(checkListDTO);
            // 유효성검사를 해줄 필요성을 잘 모르겠음. -> 더 생각해보기
        }catch (Exception e){
            e.printStackTrace(); // 수정해야함. -> 저장 실패라고 말해야함.
        }
    }


    // command 2. 특정 체크리스트에 특정 업무 추가 -> 이건 url을 어떻게 짜느냐에 따라 다를거같은데 그냥 이걸로 하면 될듯?
    @Override
    @Transactional
    public void insertTaskToCheckList(int checkListCode, int taskCode){

        // domain service에서 유효성검사 진행
        domainCheckListService.validateAndAddTaskToCheckList(checkListCode, taskCode);

        try{
            infraCheckListService.insertTaskPerCheckList(checkListCode, taskCode);
        } catch (Exception e){
            e.printStackTrace();  // 수정필요 -> taskPerCheckList 등록 실패라고 말해줘야함.
        }
    }


    // command 3. 특정 체크리스트에 특정 업무 삭제 // 신규 -> TaskPerChecklist에 있는지 확인
    @Override
    @Transactional
    public void deleteTaskFromCheckList(int checkListCode, int taskCode){
        // infra 부르고 거기서 taskperchecklist 부르기
        try{
            infraCheckListService.deleteTaskPerCheckListByCheckListCodeAndTaskCode(checkListCode, taskCode);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    // command 4. 체크리스트 자체 수정 -> 체크리스트 수정이 체크리스트 자체 수정 + 체크리스트 업무 수정이 있음. 이건 자체 수정
    @Override
    @Transactional
    public void modifyCheckList(int checkListCode, CheckListDTO checkListDTO){

        CheckList checkList = checkListRepository.findById(checkListCode).get();//.orElseThrow(CheckListNotFoundException::new);

        checkList.modify(checkListDTO);
        checkListRepository.save(checkList);
    }


    // command 5. 체크리스트 삭제
    @Override
    @Transactional
    public void deleteCheckList(int checkListCode){

        CheckList checkList = checkListRepository.findById(checkListCode).get();//.orElseThrow(CheckListNotFoundException::new);
        CheckListDTO checkListDTO = modelMapper.map(checkList, CheckListDTO.class);
        checkList.modify(checkListDTO);

        try{
            // 1. checklist flag 바꾸기
            checkListRepository.save(checkList);
            infraCheckListService.deleteTaskPerCheckListByCheckListCode(checkListCode);

        } catch (Exception e){
            e.printStackTrace(); // 수정해야함.
        }
    }
}
