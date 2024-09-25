package com.intbyte.wizbuddy.user.query.service;

import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserTypeEnum;
import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import com.intbyte.wizbuddy.user.query.repository.EmployerMapper;
import com.intbyte.wizbuddy.user.query.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service("employerQueryService")
public class EmployerService {

    private final EmployerMapper employerMapper;
    private final UserMapper userMapper;
    private final ModelMapper mapper;

    public List<UserDTO> findAllEmployerUser() {
        List<UserDTO> employerQueryDTOList = new ArrayList<>();

        for (UserEntity employer : employerMapper.getAllEmployer(UserTypeEnum.EMPLOYER.getUserType())) {
            UserDTO employerQueryDTO = mapper.map(employer, UserDTO.class);

            employerQueryDTOList.add(employerQueryDTO);
        }

        return employerQueryDTOList;
    }

    public UserDTO getByEmployerCode(String userCode) {
        UserDTO userDTO = userMapper.getEmployer(userCode, UserTypeEnum.EMPLOYER.getUserType());

        if (userDTO == null) throw new CommonException(StatusEnum.USER_NOT_FOUND);

        return userDTO;
    }
}
