package com.intbyte.wizbuddy.board.command.domain.entity;

import com.intbyte.wizbuddy.board.domain.DeleteNoticeBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditNoticeBoardInfo;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notice_code")
    private int noticeCode;

    @Column(name="notice_title")
    private String noticeTitle;

    @Column(name="notice_content")
    private String noticeContent;

    @Column(name="notice_flag")
    @ColumnDefault("true")
    private boolean noticeFlag;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="shop_code")
    private int shopCode;

    @Column(name="employer_code")
    private String employerCode;

    public void modify(EditNoticeBoardInfo modifyNoticeBoardInfo) {
        this.noticeTitle = modifyNoticeBoardInfo.getNoticeTitle();
        this.noticeContent = modifyNoticeBoardInfo.getNoticeContent();
        this.imageUrl = modifyNoticeBoardInfo.getImageUrl();
        this.updatedAt = LocalDateTime.now();
    }

    public void delete(DeleteNoticeBoardInfo deleteNoticeBoardInfo) {
        this.noticeFlag = deleteNoticeBoardInfo.isNoticeFlag();
        this.updatedAt = deleteNoticeBoardInfo.getUpdatedAt();
    }
}
