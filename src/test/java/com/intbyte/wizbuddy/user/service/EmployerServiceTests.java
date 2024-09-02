package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.user.domain.DeleteEmployerInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployerInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employer;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        EditEmployerInfo employerInfo = new EditEmployerInfo(employerCode, "test", "test", "010-9999-9998", LocalDateTime.now());

        //when
        employerService.modifyEmployer(employerInfo);

        //then
        List<Employer> newEmployers = employerRepository.findAll();
        assertEquals(newEmployers.get(0).getEmployerName(), employerInfo.getEmployerName());
    }

    @Test
    @DisplayName("사장 삭제 성공")
    @Transactional
    void testDeleteEmployerSuccess() {
        //given
        List<Employer> employers = employerRepository.findAll();
        String employerCode = employers.get(0).getEmployerCode();

        DeleteEmployerInfo deleteEmployerInfo = new DeleteEmployerInfo(employerCode,false, LocalDateTime.now());

        //when
        employerService.deleteEmployer(deleteEmployerInfo);

        //then
        List<Employer> newEmployers = employerRepository.findAll();
        assertEquals(false, newEmployers.get(0).isEmployerFlag());
    }
}