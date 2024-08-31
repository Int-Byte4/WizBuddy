package com.intbyte.wizbuddy.comment.domain.Entity;


import com.intbyte.wizbuddy.comment.domain.EditCommentInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity (name = "comment")
@Table (name = "comments")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Comment {

    @Id
    @Column(name = "comment_code")
    private int commentCode;

    @Column(name = "comment_contents")
    private String commentContent;

    @Column(name = "comment_flag")
    @ColumnDefault("true")
    private boolean commentFlag;

    @Column(name = "comment_adopted_state")
    private boolean commentAdoptedState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "subs_code")
    private int subsCode;

    @Column(name = "employee_code")
    private int employeeCode;

    public void toUpdate(@Valid EditCommentInfo editCommentInfo) {
        this.commentContent = editCommentInfo.getCommentContent();
        this.commentAdoptedState = editCommentInfo.isCommentAdoptedState();
        this.updatedAt = editCommentInfo.getUpdatedAt();
    }

    public void toDelete() {
        this.commentFlag = false;
    }
}
