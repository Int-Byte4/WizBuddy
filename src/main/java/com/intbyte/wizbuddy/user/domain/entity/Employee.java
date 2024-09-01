package com.intbyte.wizbuddy.user.domain.entity;

import com.intbyte.wizbuddy.user.domain.DeleteEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.DeleteEmployerInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployeeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "employee")
@Table(name = "employee")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_code")
    private int employeeCode;

    @Column(name="employee_name")
    private String employeeName;

    @Column(name = "employee_email")
    private String employeeEmail;

    @Column(name="employee_password")
    private String employeePassword;

    @Column(name = "employee_phone")
    private String employeePhone;

    @Column(name = "employee_flag")
    private boolean employeeFlag;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name="employee_wage")
    private int employeeWage;

    @Column(name="employee_health_date")
    private LocalDate employeeHealthDate;

    @Column(name = "employee_black_state")
    private boolean employeeBlackState;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public void modify(EditEmployeeInfo modifyEmployeeInfo) {
        this.employeeName = modifyEmployeeInfo.getEmployeeName();
        this.employeePassword = modifyEmployeeInfo.getEmployeePassword();
        this.employeePhone = modifyEmployeeInfo.getEmployeePhone();
        this.updatedAt = LocalDateTime.now();
    }

    public void removeRequest(DeleteEmployeeInfo deleteEmployeeInfo) {
        this.employeeCode = deleteEmployeeInfo.getEmployeeCode();
        this.employeeFlag = false;
        this.updatedAt = LocalDateTime.now();
    }
}
