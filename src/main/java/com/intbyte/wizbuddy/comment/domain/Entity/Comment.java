package com.intbyte.wizbuddy.comment.domain.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity (name = "comment")
@Table (name = "comments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

    @Id
    @Column(name = "comment_code")
    private int commentCode;

    @Column(name = "comment_contents")
    private String comment_contents;

    @Column(name = "comment_flag")
    private boolean comment_flag;

    @Column(name = "comment_adopted_state")
    private boolean comment_adopted_state;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "subs_code")
    private int subsCode;

    @Column(name = "employee_code")
    private int employeeCode;
}
