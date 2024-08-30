package com.intbyte.wizbuddy.user.domain.entity;

import com.intbyte.wizbuddy.user.domain.DeleteEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.DeleteEmployerInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployeeInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column
    private int employeeCode;

    @Column
    private String employeeName;

    @Column
    private String employeeEmail;

    @Column
    private String employeePassword;

    @Column
    private String employeePhone;

    @Column
    private boolean employeeFlag;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @Column
    private int employeeWage;

    @Column
    private LocalDate employeeHealthDate;

    @Column
    private boolean employeeBlackState;

    @Column
    private LocalDateTime createdAt;

    @Column
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
