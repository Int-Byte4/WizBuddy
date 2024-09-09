package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployerDTO;
import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployerRepository;
import com.intbyte.wizbuddy.user.query.repository.EmployerMapper;
import com.intbyte.wizbuddy.user.command.domain.entity.Employer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service("employerCommandService")
public class EmployerService {

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;
    private final ModelMapper mapper;

    @Transactional
    public void modifyEmployer(String employerCode, RequestEditEmployerDTO employerDTO) {
        Employer employer = employerMapper.getEmployer(employerCode);

        if (employer == null) throw new CommonException(StatusEnum.USER_NOT_FOUND);

        employer.modify(employerDTO);
        employerRepository.save(employer);
    }

    @Transactional
    public void deleteEmployer(String employerCode) {
        Employer employer = employerMapper.getEmployer(employerCode);

        if (employer == null) throw new CommonException(StatusEnum.USER_NOT_FOUND);

        employer.removeRequest(employer);
        employerRepository.save(employer);
    }
}
