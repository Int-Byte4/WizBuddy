package com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestLoginVO {
    private String userEmail;
    private String userPassword;
}
