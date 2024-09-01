package com.intbyte.wizbuddy.board.domain.entity;

import com.intbyte.wizbuddy.board.domain.EditSubsBoardInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "subsBoard")
@Table(name = "substitution_board")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class SubsBoard {
    @Id
    @Column(name = "subs_code")
    private int subsCode;

    @Column(name = "subs_title")
    private String subsTitle;

    @Column(name = "subs_content")
    private String subsContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "subs_flag" )
    private boolean subsFlag;

    @Column(name = "working_part_code")
    private int employeeWorkingPartCode;

    @Column(name = "shop_code")
    private int shopCode;


    public void toUpdate(@Valid EditSubsBoardInfo editSubsBoardInfo) {
        this.subsTitle = editSubsBoardInfo.getSubsTitle();
        this.subsContent = editSubsBoardInfo.getSubsContent();
        this.updatedAt = LocalDateTime.now();
        this.employeeWorkingPartCode = editSubsBoardInfo.getEmployeeWorkingPartCode();
    }

    public void toDelete() {
        this.subsFlag = false;
    }

}
