package com.intbyte.wizbuddy.comment.command.domain.aggregate;


import com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.EditCommentVO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity (name = "commandComment")
@Table (name = "comments")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_code")
    private int commentCode;

    @Column(name = "comment_contents")
    private String commentContent;

    @Column(name = "comment_flag")
    @ColumnDefault("true")
    private boolean commentFlag;

    @Column(name = "comment_adopted_state")
    @ColumnDefault("false")
    private boolean commentAdoptedState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "subs_code")
    private int subsCode;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "shop_code")
    private int shopCode;

    public void toUpdate(@Valid EditCommentVO editCommentVO) {
        this.commentContent = editCommentVO.getCommentContent();
        this.commentAdoptedState = editCommentVO.isCommentAdoptedState();
        this.updatedAt = editCommentVO.getUpdatedAt();
    }

    public void toDelete() {
        this.updatedAt = LocalDateTime.now();
        this.commentFlag = false;
    }

    public void toAdopt() {
        this.updatedAt = LocalDateTime.now();
        this.commentAdoptedState = true;
    }
}
