package com.intbyte.wizbuddy.schedule.domain.entity;


import jakarta.persistence.*;
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


}
