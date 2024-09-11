package com.intbyte.wizbuddy.user.command.domain.aggregate;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployeeDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "employee")
@Table(name = "employee")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee {

    @Id
    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name="employee_name")
    private String employeeName;

    @Column(nullable = false, name = "employee_email")
    private String employeeEmail;

    @Column(nullable = false, name="employee_password")
    private String employeePassword;

    @Column(nullable = false, name = "employee_phone")
    private String employeePhone;

    @Column(nullable = false, name = "employee_flag")
    private boolean employeeFlag;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name="employee_wage")
    private int employeeWage;

    @Column(name="employee_health_date")
    private LocalDate employeeHealthDate;

    @Column(nullable = false, name = "employee_black_state")
    private boolean employeeBlackState;

    @Column(nullable = false, name="created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name="updated_at")
    private LocalDateTime updatedAt;

    public void modify(RequestEditEmployeeDTO employeeDTO) {
        this.employeePassword = employeeDTO.getEmployeePassword();
        this.employeePhone = employeeDTO.getEmployeePhone();
        this.employeeHealthDate = employeeDTO.getEmployeeHealthDate();
        this.updatedAt = LocalDateTime.now();
    }

    public void removeRequest(Employee employee) {
        this.employeeCode = employee.getEmployeeCode();
        this.employeeFlag = false;
        this.updatedAt = LocalDateTime.now();
    }
}
