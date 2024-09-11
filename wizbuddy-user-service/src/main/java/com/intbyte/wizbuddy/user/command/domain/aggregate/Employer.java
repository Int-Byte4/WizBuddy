package com.intbyte.wizbuddy.user.command.domain.aggregate;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployerDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "employer")
@Table(name = "employer")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employer {

    @Id
    @Column(name = "employer_code")
    private String employerCode;

    @Column(name = "employer_name")
    private String employerName;

    @Column(nullable = false, name = "employer_email")
    private String employerEmail;

    @Column(nullable = false, name = "employer_password")
    private String employerPassword;

    @Column(nullable = false, name = "employer_phone")
    private String employerPhone;

    @Column(nullable = false, name = "employer_flag")
    private boolean employerFlag;

    @Column(nullable = false, name = "employer_black_state")
    private boolean employerBlackState;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    public void modify(RequestEditEmployerDTO employerDTO) {
        this.employerPassword = employerDTO.getEmployerPassword();
        this.employerPhone = employerDTO.getEmployerPhone();
        this.updatedAt = LocalDateTime.now();
    }

    public void removeRequest(Employer employer) {
        this.employerCode = employer.getEmployerCode();
        this.employerFlag = false;
        this.updatedAt = LocalDateTime.now();
    }
}
