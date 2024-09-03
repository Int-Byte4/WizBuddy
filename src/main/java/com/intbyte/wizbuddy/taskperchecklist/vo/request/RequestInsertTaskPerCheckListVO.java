package com.intbyte.wizbuddy.taskperchecklist.vo.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class RequestInsertTaskPerCheckListVO {

//    private int checkListCode;
//    private int taskCode;

    private final Boolean taskFinishedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String employeeCode;

    @JsonCreator
    public RequestInsertTaskPerCheckListVO(
            @JsonProperty("taskFinishedState") Boolean taskFinishedState,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("updatedAt") LocalDateTime updatedAt,
            @JsonProperty("employeeCode") String employeeCode) {
        this.taskFinishedState = taskFinishedState;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.employeeCode = employeeCode;
    }
}
