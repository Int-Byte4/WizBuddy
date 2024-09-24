package com.intbyte.wizbuddy.board.command.infrastructure.service;

import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;

public interface InfraSubsBoardService {
    void SubsBoardByCommentFlag(int subsCode);

    EmployeeWorkingPart getEmployeeWorkingPartCode(int employeeWorkingPartCode);
}
