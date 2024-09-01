package com.intbyte.wizbuddy.user.vo.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestLoginVO {
    private String userEmail;
    private String userPassword;
}
