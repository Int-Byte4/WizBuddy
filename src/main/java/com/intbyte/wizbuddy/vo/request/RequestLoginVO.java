package com.intbyte.wizbuddy.vo.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestLoginVO {
    private String email;
    private String password;
}
