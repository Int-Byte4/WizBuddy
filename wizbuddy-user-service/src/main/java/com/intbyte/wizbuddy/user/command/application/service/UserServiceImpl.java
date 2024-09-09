package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.command.domain.entity.Employee;
import com.intbyte.wizbuddy.user.command.domain.entity.Employer;
import com.intbyte.wizbuddy.user.command.domain.entity.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployeeRepository;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployerRepository;
import com.intbyte.wizbuddy.user.command.domain.repository.UserRepository;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestRegisterEmployeeVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestRegisterEmployerVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.request.RequestSignInUserVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.response.ResponseInsertEmployeeVO;
import com.intbyte.wizbuddy.user.command.domain.entity.vo.response.ResponseInsertEmployerVO;
import com.intbyte.wizbuddy.user.query.dto.EmployeeDTO;
import com.intbyte.wizbuddy.user.query.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Service("userCommandService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ResponseInsertEmployerVO signInEmployer(RequestSignInUserVO requestSignInUserVO, RequestRegisterEmployerVO registerEmployerInfo) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString();
        String customUserCode = currentDate + uuid.substring(8);

        requestSignInUserVO.setUserCode(customUserCode);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        if (userRepository.findByUserEmail(requestSignInUserVO.getUserEmail()) != null) throw new CommonException(StatusEnum.EMAIL_DUPLICATE);

        requestSignInUserVO.setUserPassword(bCryptPasswordEncoder.encode(requestSignInUserVO.getUserPassword()));
        registerEmployerInfo.setEmployerPassword(bCryptPasswordEncoder.encode(registerEmployerInfo.getEmployerPassword()));

        if (requestSignInUserVO.getUserType().equals("EMPLOYER")) requestSignInUserVO.setUserType("Employer");

        UserDTO userDTO = userDTOBuild(requestSignInUserVO);
        EmployerDTO employerQueryDTO = employerDTOBuild(requestSignInUserVO, registerEmployerInfo);

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        Employer employer = modelMapper.map(employerQueryDTO, Employer.class);

        userRepository.save(userEntity);
        employerRepository.save(employer);

        return new ResponseInsertEmployerVO(requestSignInUserVO, registerEmployerInfo);
    }

    @Transactional
    @Override
    public ResponseInsertEmployeeVO signInEmployee(RequestSignInUserVO requestSignInUserVO, RequestRegisterEmployeeVO registerEmployeeInfo) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString();
        String customUserCode = currentDate + uuid.substring(8);

        requestSignInUserVO.setUserCode(customUserCode);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        if (userRepository.findByUserEmail(requestSignInUserVO.getUserEmail()) != null) throw new CommonException(StatusEnum.EMAIL_DUPLICATE);

        requestSignInUserVO.setUserPassword(bCryptPasswordEncoder.encode(requestSignInUserVO.getUserPassword()));
        registerEmployeeInfo.setEmployeePassword(bCryptPasswordEncoder.encode(registerEmployeeInfo.getEmployeePassword()));

        if (requestSignInUserVO.getUserType().equals("EMPLOYEE")) requestSignInUserVO.setUserType("Employee");

        UserDTO userDTO = userDTOBuild(requestSignInUserVO);
        EmployeeDTO employeeDTO = employeeDTOBuild(requestSignInUserVO, registerEmployeeInfo);

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        userRepository.save(userEntity);
        employeeRepository.save(employee);

        return new ResponseInsertEmployeeVO(requestSignInUserVO, registerEmployeeInfo);
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserEntity loginUserEntity = userRepository.findByUserEmail(userEmail);

        if (loginUserEntity == null) throw new UsernameNotFoundException(userEmail + " 이메일 아이디의 유저는 존재하지 않습니다.");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        switch (loginUserEntity.getUserType()) {
            case "Employee" -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
            case "Employer" -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYER"));
            case "Admin" -> {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYER"));
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
            }
        }

        return new User(loginUserEntity.getUserEmail(), loginUserEntity.getUserPassword(), true, true, true, true, grantedAuthorities);
    }

    private UserDTO userDTOBuild(RequestSignInUserVO requestSignInUserVO) {
        return UserDTO.builder()
                .userCode(requestSignInUserVO.getUserCode())
                .userEmail(requestSignInUserVO.getUserEmail())
                .userPassword(requestSignInUserVO.getUserPassword())
                .userType(requestSignInUserVO.getUserType())
                .build();
    }

    private EmployerDTO employerDTOBuild(RequestSignInUserVO requestSignInUserVO, RequestRegisterEmployerVO requestRegisterEmployerVO) {
        return EmployerDTO.builder()
                .employerCode(requestSignInUserVO.getUserCode())
                .employerName(requestRegisterEmployerVO.getEmployerName())
                .employerEmail(requestSignInUserVO.getUserEmail())
                .employerPassword(requestSignInUserVO.getUserPassword())
                .employerPhone(requestRegisterEmployerVO.getEmployerPhone())
                .employerFlag(true)
                .employerBlackState(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private EmployeeDTO employeeDTOBuild(RequestSignInUserVO requestSignInUserVO, RequestRegisterEmployeeVO requestRegisterEmployeeVO) {
        return EmployeeDTO.builder()
                .employeeCode(requestSignInUserVO.getUserCode())
                .employeeName(requestRegisterEmployeeVO.getEmployeeName())
                .employeeEmail(requestSignInUserVO.getUserEmail())
                .employeePassword(requestSignInUserVO.getUserPassword())
                .employeePhone(requestRegisterEmployeeVO.getEmployeePhone())
                .employeeFlag(true)
                .latitude(null)
                .longitude(null)
                .employeeHealthDate(null)
                .employeeBlackState(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
