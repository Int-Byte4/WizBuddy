package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.EmailDuplicateException;
import com.intbyte.wizbuddy.user.domain.entity.Employer;
import com.intbyte.wizbuddy.user.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployerService {
    private final EmployerRepository employerRepository;
//    private final PasswordEncoder encoder;

    // 회원가입
    public void employerRegister(EmployerDTO info) throws Exception {
        employerRepository.findByEmployerEmail(info.getEmployerEmail())
                .ifPresent(employer -> {
                    try {
                        throw new EmailDuplicateException();
                    } catch (EmailDuplicateException e) {
                        throw new RuntimeException(e);
                    }
                });
        Employer employer = userBuilder(info);
        try {
            employerRepository.save(employer);
        } catch (Exception e) {
            log.error("e.getMessage={}", e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    }

    private Employer userBuilder(EmployerDTO info) {
        Employer employer = Employer.builder()
                .employerEmail(info.getEmployerEmail())
//                .employerPassword(encoder.encode(info.getEmployerEmail()))
                .employerName(info.getEmployerName())
                .employerPhone(info.getEmployerPhone())
                .build();
        return employer;
    }
}
