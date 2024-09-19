package com.intbyte.wizbuddy.user.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "employeeadditional")
@Table(name = "employeeadditional")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmployeeAdditional {

    @Id
    @Column(name = "employee_code")
    private String userCode;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "employee_wage")
    private int employeeWage;

    @Column(name = "employee_health_date")
    private LocalDate employeeHealthDate;
}

