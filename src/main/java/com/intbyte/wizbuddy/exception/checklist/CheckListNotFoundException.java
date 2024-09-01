package com.intbyte.wizbuddy.exception.checklist;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class CheckListNotFoundException extends IllegalArgumentException{

    private final StatusEnum status;

    private static final String message = "해당 매장이 존재하지 않습니다.";

    public CheckListNotFoundException() {
        super(message);
        this.status = StatusEnum.SHOP_NOT_FOUND;
    }
}
