package com.intbyte.wizbuddy.board.domain.entity;

import com.intbyte.wizbuddy.board.domain.DeleteNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditNoticeBoardInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity(name = "noticeBoard")
@Table(name = "notice_board")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NoticeBoard {
    @Id
    @Column
    private int noticeCode;

    @Column
    private String noticeTitle;

    @Column
    private String noticeContent;

    @Column
// @ColumnDefault("true")
    private boolean noticeFlag;

    @Column
    private String imageUrl;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private int shopCode;

    public void modify(EditNoticeBoardInfo modifyNoticeBoardInfo) {
        this.noticeTitle = modifyNoticeBoardInfo.getNoticeTitle();
        this.noticeContent = modifyNoticeBoardInfo.getNoticeContent();
        this.imageUrl = modifyNoticeBoardInfo.getImageUrl();
        this.updatedAt = LocalDateTime.now();
    }

    public void delete(DeleteNoticeBoardInfo deleteNoticeBoardInfo) {
        this.noticeFlag = false;
        this.updatedAt = LocalDateTime.now();
    }
}
