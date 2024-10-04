package com.intbyte.wizbuddy.checklist.command.domain.aggregate.entity;

import com.intbyte.wizbuddy.checklist.command.application.dto.CheckListDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "check_list")
@Table(name = "check_list")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode
public class CheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="checklist_code")
    private int checkListCode;

    @Column(name="checklist_title")
    private String checkListTitle;

    @Column(name="checklist_flag")
    private Boolean checkListFlag;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="shop_code")
    private Integer shopCode;

    public void modify(@Valid CheckListDTO checkListDTO){
        this.checkListTitle = checkListDTO.getCheckListTitle();
        this.checkListFlag = checkListDTO.isCheckListFlag();
        this.updatedAt = LocalDateTime.now();
    }
}