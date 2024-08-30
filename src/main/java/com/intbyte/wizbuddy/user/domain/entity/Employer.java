package com.intbyte.wizbuddy.user.domain.entity;

import com.intbyte.wizbuddy.user.domain.DeleteEmployerInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployerInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "employer")
@Table(name = "employer")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_code")
    private int employerCode;

    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "employer_email")
    private String employerEmail;

    @Column(name = "employer_password")
    private String employerPassword;

    @Column(name = "employer_phone")
    private String employerPhone;

    @Column(name = "employer_flag")
    private boolean employerFlag;

    @Column(name = "employer_black_state")
    private boolean employerBlackState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void modify(EditEmployerInfo modifyEmployerInfo) {
        this.employerName = modifyEmployerInfo.getEmployerName();
        this.employerPassword = modifyEmployerInfo.getEmployerPassword();
        this.employerPhone = modifyEmployerInfo.getEmployerPhone();
        this.updatedAt = LocalDateTime.now();
    }

    public void removeRequest(DeleteEmployerInfo deleteEmployerInfo) {
        this.employerCode = deleteEmployerInfo.getEmployerCode();
        this.employerFlag = false;
        this.updatedAt = LocalDateTime.now();
    }
}
