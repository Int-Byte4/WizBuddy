package com.intbyte.wizbuddy.exception.shop;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class ShopModifyOtherEmployerException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "해당 매장이 존재하지 않습니다.";

    public ShopModifyOtherEmployerException() {
        super(message);
        this.status = StatusEnum.RESTRICTED;
    }
}
