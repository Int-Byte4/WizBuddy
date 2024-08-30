package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.UserNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployerMapper;
import com.intbyte.wizbuddy.user.domain.DeleteEmployerInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployerInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employer;
import com.intbyte.wizbuddy.user.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployerService {

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;

    // user에서 회원가입, 로그인 / employer에서는 등록만 된다.
    @Transactional
    public void registerEmployer(EmployerDTO employerInfo) {
        Employer employer = Employer.builder()
                .employerName(employerInfo.getEmployerName())
                .employerEmail(employerInfo.getEmployerEmail())
                .employerPhone(employerInfo.getEmployerPhone())
                .employerFlag(employerInfo.isEmployerFlag())
                .employerBlackState(employerInfo.isEmployerBlackState())
                .createdAt(employerInfo.getCreatedAt())
                .updatedAt(employerInfo.getUpdatedAt())
                .build();

        employerRepository.save(employer);
    }

    @Transactional
    public void modifyEmployer(EditEmployerInfo modifyEmployerInfo) {
        int employerCode = modifyEmployerInfo.getEmployerCode();

        Employer employer = employerMapper.getEmployer(employerCode);

        if (employer == null) throw new UserNotFoundException();

        employer.modify(modifyEmployerInfo);
        employerRepository.save(employer);
    }

    @Transactional
    public void deleteEmployer(DeleteEmployerInfo deleteEmployerInfo) {
        int employerCode = deleteEmployerInfo.getEmployerCode();

        Employer employer = employerMapper.getEmployer(employerCode);

        if (employer == null) throw new UserNotFoundException();

        employer.removeRequest(deleteEmployerInfo);
        employerRepository.save(employer);
    }
}
