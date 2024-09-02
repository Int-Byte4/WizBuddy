package com.intbyte.wizbuddy.user.domain.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEmployerInfo {
    private String employerCode;
    private boolean employerFlag;
    private LocalDateTime updatedAt;
}
