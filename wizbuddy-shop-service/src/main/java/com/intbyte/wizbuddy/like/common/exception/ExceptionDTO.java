package com.intbyte.wizbuddy.like.common.exception;

import lombok.Getter;
//필기. 에러 응답 형식(코드,메시지)
@Getter
public class ExceptionDTO {
    private final Integer statusCode;
    private final String message;
    public ExceptionDTO(StatusEnum statusEnum){
        this.statusCode = statusEnum.getStatusCode();
        this.message = statusEnum.getMessage();
    }

    public static ExceptionDTO of(StatusEnum errorCode){
        return new ExceptionDTO(errorCode);
    }
}
