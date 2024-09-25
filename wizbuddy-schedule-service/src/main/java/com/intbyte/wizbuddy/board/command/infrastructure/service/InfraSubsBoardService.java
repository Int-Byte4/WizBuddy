package com.intbyte.wizbuddy.board.command.infrastructure.service;

import com.intbyte.wizbuddy.board.query.application.dto.SubsBoardDTO;
import com.intbyte.wizbuddy.employeeworkingpart.query.dto.EmployeeWorkingPartDTO;

public interface InfraSubsBoardService {

    void SubsBoardByCommentFlag(int subsCode);

    EmployeeWorkingPartDTO getEmployeeWorkingPartCode(int employeeWorkingPartCode);

}
