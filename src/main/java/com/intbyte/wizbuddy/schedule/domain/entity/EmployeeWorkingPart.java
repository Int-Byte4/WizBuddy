package com.intbyte.wizbuddy.schedule.domain.entity;

import com.intbyte.wizbuddy.schedule.domain.EditScheduleInfo;
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

    public void modify(@Valid EditScheduleInfo modifyWorkingPartInfo) {
        this.employeeCode = modifyWorkingPartInfo.getEmployeeCode();
    }

}
