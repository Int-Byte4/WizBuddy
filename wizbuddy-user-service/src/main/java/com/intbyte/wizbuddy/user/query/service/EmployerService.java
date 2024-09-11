package com.intbyte.wizbuddy.user.query.service;

import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.query.repository.EmployerMapper;
import com.intbyte.wizbuddy.user.command.domain.aggregate.Employer;
import com.intbyte.wizbuddy.user.query.dto.EmployerDTO;
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
    private final ModelMapper mapper;

    public EmployerDTO getByEmployerCode(String employerCode) {
        Employer employer = employerMapper.getEmployer(employerCode);

        if (employer == null) throw new CommonException(StatusEnum.USER_NOT_FOUND);

        EmployerDTO employerQueryDTO = mapper.map(employer, EmployerDTO.class);

        return employerQueryDTO;
    }

    public List<EmployerDTO> findAllEmployer() {
        List<EmployerDTO> employerQueryDTOList = new ArrayList<>();

        for (Employer employer : employerMapper.getAllEmployer()) {
            EmployerDTO employerQueryDTO = mapper.map(employer, EmployerDTO.class);

            employerQueryDTOList.add(employerQueryDTO);
        }

        return employerQueryDTOList;
    }
}
