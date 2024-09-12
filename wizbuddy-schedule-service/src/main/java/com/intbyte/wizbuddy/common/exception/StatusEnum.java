package com.intbyte.wizbuddy.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum StatusEnum {

    WRONG_ENTRY_POINT(400, HttpStatus.BAD_REQUEST, "잘못된 접근입니다"),
    MISSING_REQUEST_PARAMETER(400, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    DATA_INTEGRITY_VIOLATION(400, HttpStatus.BAD_REQUEST, "데이터 무결성 위반입니다. 필수 값이 누락되었거나 유효하지 않습니다."),
    INVALID_PARAMETER_FORMAT(400, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자 형식입니다."),
    EMAIL_DUPLICATE(400, HttpStatus.BAD_REQUEST, "이미 등록된 이메일입니다."),
    ALREADY_PUSH_LIKED(400, HttpStatus.BAD_REQUEST, "이미 좋아요 달린 글입니다."),
    SHOP_IS_NOT_EQUAL(400, HttpStatus.BAD_REQUEST, "매장이 동일하지 않습니다."),
    TASK_PER_CHECKLIST_DUPLICATE(400, HttpStatus.BAD_REQUEST, "이미 체크리스트에 해당 업무가 등록되어 있습니다."),
    WORKING_DATE_AND_TIME_EQUALS(400, HttpStatus.BAD_REQUEST, "이미 같은 시간에 근무하는 직원입니다."),
    SCHEDULE_CODE_DUPLICATE(400, HttpStatus.BAD_REQUEST, "이미 등록된 스케줄입니다."),

    RESTRICTED(403, HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    USER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    EMPLOYEE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    EMPLOYER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    BOARD_NOT_FOUND(404, HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    CHECKLIST_NOT_FOUND(404, HttpStatus.NOT_FOUND, "체크리스트가 존재하지 않습니다."),
    TASK_NOT_FOUND(404, HttpStatus.NOT_FOUND, "업무가 존재하지 않습니다."),
    TASK_PER_CHECKLIST_NOT_FOUND(404, HttpStatus.NOT_FOUND, "체크리스트에 해당 업무가 존재하지 않습니다."),
    SHOP_NOT_FOUND(404, HttpStatus.NOT_FOUND, "매장이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(404, HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."),
    SCHEDULE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "해당 스케줄이 존재하지 않습니다."),
    EMPLOYEE_CODE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "해당 코드를 가진 직원이 존재하지 않습니다."),

    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다");
    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;

    StatusEnum(int statusCode, HttpStatus httpStatus, String message) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

