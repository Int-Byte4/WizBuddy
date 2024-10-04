package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.aggregate.SubsBoard;
import com.intbyte.wizbuddy.board.command.domain.aggregate.vo.EditSubsBoardVO;
import com.intbyte.wizbuddy.board.command.domain.repository.SubsBoardRepository;

import com.intbyte.wizbuddy.board.command.infrastructure.client.ShopServiceClient;
import com.intbyte.wizbuddy.board.command.infrastructure.dto.ShopDTO;
import com.intbyte.wizbuddy.board.command.infrastructure.event.SubsBoardDeletedEvent;
import com.intbyte.wizbuddy.board.command.infrastructure.service.InfraSubsBoardService;
import com.intbyte.wizbuddy.board.vo.response.ResponseDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseModifySubsBoardVO;
import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("commandSubsBoardService")
@RequiredArgsConstructor
public class SubsBoardServiceImpl implements SubsBoardService {

    private final ModelMapper modelMapper;
    private final SubsBoardRepository subsBoardRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ShopServiceClient shopServiceClient;
    private final InfraSubsBoardService infraSubsBoardService;

    @Transactional
    @Override
    public ResponseInsertSubsBoardVO registSubsBoard(SubsBoardDTO subsBoardDTO) {

        if (subsBoardDTO.getSubsTitle() == null || subsBoardDTO.getSubsTitle().isEmpty()
                || subsBoardDTO.getSubsContent() == null || subsBoardDTO.getSubsContent().isEmpty()) {
            throw new CommonException(StatusEnum.INVALID_SUBS_BOARD_DATA);
        }

        ShopDTO shop = shopServiceClient.getShop(subsBoardDTO.getShopCode());
        if (shop == null || shop.getShopCode() == 0) {
            throw new CommonException(StatusEnum.SHOP_NOT_FOUND);
        }

        LocalDateTime now = LocalDateTime.now();

        EmployeeWorkingPartDTO employeeWorkingPart = infraSubsBoardService.getEmployeeWorkingPartCode(subsBoardDTO.getEmployeeWorkingPartCode());
        if (employeeWorkingPart == null || employeeWorkingPart.getWorkingPartCode() == 0) {
            throw new CommonException(StatusEnum.INVALID_EMPLOYEE_WORKING_PART_DATA);
        }

        SubsBoard subsBoard = SubsBoard.builder()
                .subsTitle(subsBoardDTO.getSubsTitle())
                .subsContent(subsBoardDTO.getSubsContent())
                .createdAt(now)
                .updatedAt(now)
                .subsFlag(true)
                .employeeWorkingPartCode(employeeWorkingPart.getWorkingPartCode())
                .shopCode(shop.getShopCode())
                .build();

        subsBoardRepository.save(subsBoard);

        return modelMapper.map(subsBoard, ResponseInsertSubsBoardVO.class);
    }


    @Transactional
    @Override
    public ResponseModifySubsBoardVO modifySubsBoards(int subsCode, EditSubsBoardVO modifySubsBoardInfo) {

        SubsBoard subsBoard = subsBoardRepository.findById(subsCode)
                .orElseThrow(() -> new CommonException(StatusEnum.BOARD_NOT_FOUND));

        if (modifySubsBoardInfo.getSubsTitle() == null || modifySubsBoardInfo.getSubsTitle().isEmpty()
                || modifySubsBoardInfo.getSubsContent() == null || modifySubsBoardInfo.getSubsContent().isEmpty()) {
            throw new CommonException(StatusEnum.INVALID_SUBS_BOARD_DATA);
        }

        subsBoard.toUpdate(modifySubsBoardInfo);
        subsBoardRepository.save(subsBoard);
        return modelMapper.map(subsBoard, ResponseModifySubsBoardVO.class);
    }

    @Transactional
    @Override
    public ResponseDeleteSubsBoardVO deleteSubsBoard(SubsBoardDTO deleteSubsBoardDTO) {
        SubsBoard subsBoard = subsBoardRepository.findById(deleteSubsBoardDTO.getSubsCode())
                .orElseThrow(() -> new CommonException(StatusEnum.BOARD_NOT_FOUND));
        subsBoard.toDelete();
        subsBoardRepository.save(subsBoard);
        eventPublisher.publishEvent(new SubsBoardDeletedEvent(subsBoard.getSubsCode()));
        return modelMapper.map(subsBoard, ResponseDeleteSubsBoardVO.class);
    }


}
