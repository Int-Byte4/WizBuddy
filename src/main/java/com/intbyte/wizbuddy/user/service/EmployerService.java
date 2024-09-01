package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.UserNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployerMapper;
import com.intbyte.wizbuddy.user.domain.DeleteEmployerInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployerInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employer;
import com.intbyte.wizbuddy.user.domain.entity.User;
import com.intbyte.wizbuddy.user.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.dto.UserDTO;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import com.intbyte.wizbuddy.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployerService {

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    // user에서 회원가입하면 사장 등록까지 한 번에 처리한다.
    // 수정, 삭제는 employer 에서

    @Transactional
    public void modifyEmployer(EditEmployerInfo modifyEmployerInfo) {
        String employerCode = modifyEmployerInfo.getEmployerCode();

        Employer employer = employerMapper.getEmployer(employerCode);

        if (employer == null) throw new UserNotFoundException();

        employer.modify(modifyEmployerInfo);
        employerRepository.save(employer);
    }

    @Transactional
    public void deleteEmployer(DeleteEmployerInfo deleteEmployerInfo) {
        String employerCode = deleteEmployerInfo.getEmployerCode();

        Employer employer = employerMapper.getEmployer(employerCode);

        if (employer == null) throw new UserNotFoundException();

        employer.removeRequest(deleteEmployerInfo);
        employerRepository.save(employer);
    }

    public UserDTO getByEmployerCode(String employerCode) {
        Employer employer = employerMapper.getEmployer(employerCode);

        if (employer == null) throw new UserNotFoundException();

        User user = userRepository.findByUserCode(employerCode);
        UserDTO userDTO = mapper.map(user, UserDTO.class);

        return userDTO;
    }
}
