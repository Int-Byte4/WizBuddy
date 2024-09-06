package com.intbyte.wizbuddy.task.vo.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RequestInsertTaskVO {

    private final String taskContents;
    private final boolean taskFlag;
    private final boolean taskFixedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int shopCode;

    @JsonCreator
    public RequestInsertTaskVO(
            @JsonProperty("taskContents") String taskContents,
            @JsonProperty("taskFlag") boolean taskFlag,
            @JsonProperty("taskFixedState") boolean taskFixedState,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("updatedAt") LocalDateTime updatedAt,
            @JsonProperty("shopCode") int shopCode) {
        this.taskContents = taskContents;
        this.taskFlag = taskFlag;
        this.taskFixedState = taskFixedState;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.shopCode = shopCode;
    }
}