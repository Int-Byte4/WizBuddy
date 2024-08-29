package com.intbyte.wizbuddy.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ManualBoardDTO {
    private int manual_code;
    private String manual_title;
    private String manual_contents;
    private boolean manual_flag;
    private String image_url;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int shop_code;
    private int user_code;
}
