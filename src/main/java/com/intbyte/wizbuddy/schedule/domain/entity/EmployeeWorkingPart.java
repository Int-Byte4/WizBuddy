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
    @Column
    private int workingPartCode;

    @Column
    private int employeeCode;

    @Column
    private int scheduleCode;

    @Column
    private LocalDateTime workingDate;

    @Column
    private String workingPartTime;

    public void modify(@Valid EditScheduleInfo modifyWorkingPartInfo) {
        this.employeeCode = modifyWorkingPartInfo.getEmployeeCode();
        this.workingPartTime = modifyWorkingPartInfo.getWorkingPartTime();
    }

}
