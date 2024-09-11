package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployerDTO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.Employer;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployerServiceTests {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EmployerService employerService;

    @Test
    @DisplayName("사장 정보 수정 성공")
    @Transactional
    void updateEmployerSuccess() {
        //given
        List<Employer> employers = employerRepository.findAll();
        String employerCode = employers.get(0).getEmployerCode();

        RequestEditEmployerDTO requestEditEmployerDTO = RequestEditEmployerDTO.builder()
                .employerPassword("test")
                .employerPhone("010-9999-9998")
                .updatedAt(LocalDateTime.now())
                .build();

        //when
        employerService.modifyEmployer(employerCode, requestEditEmployerDTO);

        //then
        List<Employer> newEmployers = employerRepository.findAll();
        assertEquals(newEmployers.get(0).getEmployerPhone(), requestEditEmployerDTO.getEmployerPhone());
    }

    @Test
    @DisplayName("사장 삭제 성공")
    @Transactional
    void testDeleteEmployerSuccess() {
        //given
        List<Employer> employers = employerRepository.findAll();
        String employerCode = employers.get(0).getEmployerCode();

        //when
        employerService.deleteEmployer(employerCode);

        //then
        List<Employer> newEmployers = employerRepository.findAll();
        assertEquals(false, newEmployers.get(0).isEmployerFlag());
    }
}