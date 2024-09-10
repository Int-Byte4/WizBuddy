package com.intbyte.wizbuddy.checklist.command.domain.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RequestInsertCheckListVO {

    private final String checkListTitle;
    private final Boolean checkListFlag;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Integer shopCode;

    @JsonCreator
    public RequestInsertCheckListVO(
            @JsonProperty("checkListTitle") String checkListTitle,
            @JsonProperty("checkListFlag") Boolean checkListFlag,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("updatedAt") LocalDateTime updatedAt,
            @JsonProperty("shopCode") int shopCode) {
        this.checkListTitle = checkListTitle;
        this.checkListFlag = checkListFlag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.shopCode = shopCode;
    }
}