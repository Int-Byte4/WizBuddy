package com.intbyte.wizbuddy.board.command.application.service;

import com.intbyte.wizbuddy.board.command.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.board.command.domain.aggregate.vo.EditSubsBoardInfo;
import com.intbyte.wizbuddy.board.vo.response.ResponseDeleteSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseInsertSubsBoardVO;
import com.intbyte.wizbuddy.board.vo.response.ResponseModifySubsBoardVO;

public interface SubsBoardService {

    ResponseInsertSubsBoardVO registSubsBoard(SubsBoardDTO subsBoardDTO);

    ResponseModifySubsBoardVO modifySubsBoards(int subsCode, EditSubsBoardInfo modifySubsBoardInfo);

    ResponseDeleteSubsBoardVO deleteSubsBoard(SubsBoardDTO deleteSubsBoardDTO);
}
