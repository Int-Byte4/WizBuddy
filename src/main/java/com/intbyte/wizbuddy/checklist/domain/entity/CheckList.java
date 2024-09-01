package com.intbyte.wizbuddy.checklist.domain.entity;

import com.intbyte.wizbuddy.checklist.domain.EditCheckListInfo;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
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

    @ManyToOne
    @Column(name="shop_code")
    private Shop shopCode;

    public void modify(@Valid EditCheckListInfo editCheckListInfo){
        this.checkListTitle = editCheckListInfo.getCheckListTitle();
        this.checkListFlag = editCheckListInfo.getCheckListFlag();
        this.updatedAt = editCheckListInfo.getUpdatedAt();
    }
}
