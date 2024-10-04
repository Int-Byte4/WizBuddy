package com.intbyte.wizbuddy.user.command.domain.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseEmailMessageVO {

    @JsonProperty("message")
    private String message;
}
