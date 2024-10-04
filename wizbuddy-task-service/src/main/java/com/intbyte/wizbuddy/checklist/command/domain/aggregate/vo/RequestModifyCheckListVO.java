package com.intbyte.wizbuddy.checklist.command.domain.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RequestModifyCheckListVO {

    private final String checkListTitle;
    private final Boolean checkListFlag;
    private final LocalDateTime updatedAt;

    @JsonCreator
    public RequestModifyCheckListVO(
            @JsonProperty("checkListTitle") String checkListTitle,
            @JsonProperty("checkListFlag") Boolean checkListFlag,
            @JsonProperty("updatedAt") LocalDateTime updatedAt) {
        this.checkListTitle = checkListTitle;
        this.checkListFlag = checkListFlag;
        this.updatedAt = updatedAt;
    }
}