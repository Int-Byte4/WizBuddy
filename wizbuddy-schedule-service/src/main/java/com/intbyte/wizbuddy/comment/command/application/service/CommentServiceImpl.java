package com.intbyte.wizbuddy.comment.command.application.service;

import com.intbyte.wizbuddy.board.command.infrastructure.client.ShopServiceClient;
import com.intbyte.wizbuddy.board.command.infrastructure.dto.ShopDTO;
import com.intbyte.wizbuddy.board.command.infrastructure.service.InfraSubsBoardService;
import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.comment.command.application.dto.CommentDTO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.Comment;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.EditCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseAdoptCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseDeleteCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseInsertCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response.ResponseModifyCommentVO;
import com.intbyte.wizbuddy.comment.command.domain.repository.CommentRepository;
import com.intbyte.wizbuddy.comment.command.infrastructure.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.comment.command.infrastructure.service.InfraCommentService;
import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.infrastructure.client.UserServiceClient;
import com.intbyte.wizbuddy.infrastructure.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("commandCommentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final InfraCommentService infraCommentService;
    private final UserServiceClient userServiceClient;
    private final ShopServiceClient shopServiceClient;

    @Transactional
    @Override
    public ResponseInsertCommentVO registerComment(CommentDTO comments) {

        ResponseEntity<EmployeeDTO> responseEmployee = userServiceClient.getEmployee(comments.getEmployeeCode());
        if (responseEmployee == null || responseEmployee.getBody() == null) {
            throw new CommonException(StatusEnum.USER_NOT_FOUND);
        }

        ShopDTO shop = shopServiceClient.getShop(comments.getShopCode()).getBody();
        if (shop == null || shop.getShopCode() == 0) {
            throw new CommonException(StatusEnum.SHOP_NOT_FOUND);
        }

        ResponseEntity<EmployeePerShopDTO> responseEmployeePerShop = shopServiceClient.getShopByEmployeeCode(comments.getShopCode(), comments.getEmployeeCode());
        if (responseEmployeePerShop == null || responseEmployeePerShop.getBody() == null) {
            throw new CommonException(StatusEnum.USER_NOT_FOUND);
        }

        SubsBoardDTO subsBoard = infraCommentService.findSubsBoardById(comments.getSubsCode());
        if (subsBoard == null || subsBoard.getSubsCode() == 0) {
            throw new CommonException(StatusEnum.BOARD_NOT_FOUND);
        }

        EmployeeDTO employeeDTO = responseEmployee.getBody();
        EmployeePerShopDTO employeePerShopDTO = responseEmployeePerShop.getBody();
        LocalDateTime now = LocalDateTime.now();

        if (employeePerShopDTO.getShopCode() != shop.getShopCode()) {
            throw new CommonException(StatusEnum.SHOP_NOT_FOUND);
        }

        if (!employeePerShopDTO.getEmployeeCode().equals(employeeDTO.getEmployeeCode())) {
            throw new CommonException(StatusEnum.USER_NOT_FOUND);
        }


        Comment comment = Comment.builder()
                .commentContent(comments.getCommentContent())
                .commentFlag(true)
                .commentAdoptedState(false)
                .createdAt(now)
                .updatedAt(now)
                .subsCode(subsBoard.getSubsCode())
                .employeeCode(employeePerShopDTO.getEmployeeCode())
                .shopCode(employeePerShopDTO.getShopCode())
                .build();

        commentRepository.save(comment);

        return modelMapper.map(comment, ResponseInsertCommentVO.class);
    }


    @Transactional
    @Override
    public ResponseModifyCommentVO modifyComment(int commentCode, EditCommentVO modifyCommentInfo) {
        Comment modifycomment = commentRepository.findById(commentCode).orElseThrow(() -> new CommonException(StatusEnum.COMMENT_NOT_FOUND));
        modifycomment.toUpdate(modifyCommentInfo);
        commentRepository.save(modifycomment);
        return modelMapper.map(modifycomment, ResponseModifyCommentVO.class);
    }

    @Transactional
    @Override
    public ResponseDeleteCommentVO removeComment(CommentDTO deleteComment) {
        Comment comment = commentRepository.findById(deleteComment.getCommentCode()).orElseThrow(() -> new CommonException(StatusEnum.COMMENT_NOT_FOUND));
        comment.toDelete();
        commentRepository.save(comment);
        return modelMapper.map(comment, ResponseDeleteCommentVO.class);
    }

    @Override
    public List<CommentDTO> findCommentsBySubsCode(int subsCode) {
        List<Comment> comments = commentRepository.findBySubsCode(subsCode);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ResponseAdoptCommentVO adoptComment(CommentDTO adoptComment) {
        Comment comment = commentRepository.findById(adoptComment.getCommentCode())
                .orElseThrow(() -> new CommonException(StatusEnum.COMMENT_NOT_FOUND));
        Comment existingComment = commentRepository.findBySubsCodeAndCommentAdoptedState
                (comment.getSubsCode(), true);
        if (existingComment != null) {
            throw new CommonException(StatusEnum.ALREADY_ADOPTED);
        }
        comment.toAdopt();
        commentRepository.save(comment);
        infraCommentService.handleAdoptProcess(comment);
        return modelMapper.map(comment, ResponseAdoptCommentVO.class);
    }


}
