package com.intbyte.wizbuddy.checklist.vo.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RequestModifyCheckListVO {

    //    private final int checkListCode;
    private final String checkListTitle;
    private final Boolean checkListFlag;
    //    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
//    private final int shopCode;

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