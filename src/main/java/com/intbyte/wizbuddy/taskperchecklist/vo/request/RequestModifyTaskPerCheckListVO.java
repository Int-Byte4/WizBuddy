package com.intbyte.wizbuddy.taskperchecklist.vo.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RequestModifyTaskPerCheckListVO {

    private final Boolean taskFinishedState;
    private final LocalDateTime updatedAt;
    private final String employeeCode;

    @JsonCreator
    public RequestModifyTaskPerCheckListVO(
            @JsonProperty("taskFinishedState") Boolean taskFinishedState,
            @JsonProperty("updatedAt") LocalDateTime updatedAt,
            @JsonProperty("employeeCode") String employeeCode) {
        this.taskFinishedState = taskFinishedState;
        this.updatedAt = updatedAt;
        this.employeeCode = employeeCode;
    }
//    private int checkListCode;
//    private int taskCode;
//    private LocalDateTime createdAt;
}
