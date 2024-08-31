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
    @Column
    private int manualCode;

    @Column
    private String manualTitle;

    @Column
    private String manualContents;

    @Column
    private boolean manualFlag;

    @Column
    private String imageUrl;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private int shopCode;

    @Column
    private int userCode;


    public void modify(EditManualBoardInfo modifyManualBoardInfo) {
        this.manualTitle = modifyManualBoardInfo.getManualTitle();
        this.manualContents = modifyManualBoardInfo.getManualContents();
        this.imageUrl = modifyManualBoardInfo.getImageUrl();
        this.updatedAt = LocalDateTime.now();
    }

    public void delete(DeleteManualBoardInfo deleteManualBoardInfo) {
        this.manualFlag = false;
        this.updatedAt = LocalDateTime.now();
    }
}
