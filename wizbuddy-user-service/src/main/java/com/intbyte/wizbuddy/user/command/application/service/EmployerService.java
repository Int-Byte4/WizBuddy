package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployerDTO;
import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployerRepository;
import com.intbyte.wizbuddy.user.query.repository.EmployerMapper;
import com.intbyte.wizbuddy.user.command.domain.aggregate.Employer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service("employerCommandService")
public class EmployerService {

    private final EmployerRepository employerRepository;

    @Transactional
    public void modifyEmployer(String employerCode, RequestEditEmployerDTO employerDTO, String authEmployerCode) {
        Employer employer = getEmployer(employerCode, authEmployerCode);

        employer.modify(employerDTO);
        employerRepository.save(employer);
    }

    @Transactional
    public void deleteEmployer(String employerCode, String authEmployerCode) {
        Employer employer = getEmployer(employerCode, authEmployerCode);

        employer.removeRequest(employer);
        employerRepository.save(employer);
    }

    private Employer getEmployer(String employerCode, String authEmployerCode) {
        Employer employer = employerRepository.findById(employerCode)
                .orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        if (!employerCode.equals(authEmployerCode)) throw new CommonException(StatusEnum.RESTRICTED);
        return employer;
    }
}
