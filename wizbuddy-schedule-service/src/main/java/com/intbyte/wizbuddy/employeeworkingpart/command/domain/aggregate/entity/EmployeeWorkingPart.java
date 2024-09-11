package com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity;

import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.vo.response.ResponseModifyScheduleVO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "employeeWorkingPart")
@Table(name = "employeeWorkingPart")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class EmployeeWorkingPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="working_part_code")
    private int workingPartCode;

    @Column(nullable = false, name = "employee_code")
    private String employeeCode;

    @Column(nullable = false, name = "schedule_code")
    private int scheduleCode;

    @Column(nullable = false, name = "working_date")
    private LocalDateTime workingDate;

    @Column(nullable = false, name = "working_part_time")
    private String workingPartTime;

    public void modify(@Valid ResponseModifyScheduleVO responseModifyScheduleVO) {
        this.employeeCode = responseModifyScheduleVO.getEmployeeCode();
    }

    public void modifyWorkingPart(String employeeCode) {
        this.employeeCode = employeeCode;
    }
}
