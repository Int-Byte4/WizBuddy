package com.intbyte.wizbuddy.board.domain.entity;

import com.intbyte.wizbuddy.board.dto.SubsBoardDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "subsBoard")
@Table(name = "substitutionBoard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    public SubsBoard(SubsBoardDTO subsBoard) {
        this.subsCode = subsBoard.getSubsCode();
        this.subsTitle = subsBoard.getSubsTitle();
        this.subsContent = subsBoard.getSubsContent();
        this.createdAt = subsBoard.getCreatedAt();
        this.updatedAt = subsBoard.getUpdatedAt();
        this.subsFlag = subsBoard.isSubsFlag();
        this.employeeWorkingPartCode = subsBoard.getEmployeeWorkingPartCode();
        this.shopCode = subsBoard.getShopCode();
    }


    public void toUpdate(SubsBoardDTO dto) {
        this.subsTitle = dto.getSubsTitle();
        this.subsContent = dto.getSubsContent();
        this.updatedAt = LocalDateTime.now();
        this.employeeWorkingPartCode = dto.getEmployeeWorkingPartCode();
    }

    public void modifyFlag() {
        this.subsFlag = false;
    }

}
