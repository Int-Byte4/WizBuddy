package com.intbyte.wizbuddy.employeeworkingpart.common;

public class EmployeeCodeNotFoundException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "해당 직원의 근무일정이 존재하지 않습니다.";

    public EmployeeCodeNotFoundException() {
        super(message);
        this.status = StatusEnum.EMPLOYEE_CODE_NOT_FOUND;
    }
}
