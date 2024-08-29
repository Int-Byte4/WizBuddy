package com.intbyte.wizbuddy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeBoardDTO {
    private int notice_code;
    private String notice_title;
    private String notice_content;
    private boolean notice_flag;
    private String image_url;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int shop_code;
}
