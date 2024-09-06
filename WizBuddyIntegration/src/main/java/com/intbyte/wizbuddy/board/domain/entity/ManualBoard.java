package com.intbyte.wizbuddy.board.domain.entity;

import com.intbyte.wizbuddy.board.domain.DeleteManualBoardInfo;
import com.intbyte.wizbuddy.board.domain.EditManualBoardInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "manualBoard")
@Table(name = "manual_board")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ManualBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="manual_code")
    private int manualCode;

    @Column(name="manual_title")
    private String manualTitle;

    @Column(name="manual_contents")
    private String manualContents;

    @Column(name="manual_flag")
    private boolean manualFlag;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="shop_code")
    private int shopCode;

    @Column(name="user_code")
    private String userCode;


    public void modify(EditManualBoardInfo modifyManualBoardInfo) {
        this.manualTitle = modifyManualBoardInfo.getManualTitle();
        this.manualContents = modifyManualBoardInfo.getManualContents();
        this.imageUrl = modifyManualBoardInfo.getImageUrl();
        this.updatedAt = LocalDateTime.now();
    }

    public void delete(DeleteManualBoardInfo deleteManualBoardInfo) {
        this.manualFlag = deleteManualBoardInfo.isManualFlag();
        this.updatedAt = deleteManualBoardInfo.getUpdatedAt();
    }
}
