package com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response;

import com.intbyte.wizbuddy.user.query.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseInsertEmployerVO {
    private UserDTO newUser;
    private EmployerDTO newEmployer;
}
