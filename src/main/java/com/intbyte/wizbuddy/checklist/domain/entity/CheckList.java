package com.intbyte.wizbuddy.checklist.domain.entity;

import com.intbyte.wizbuddy.checklist.domain.EditCheckListInfo;
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
    @Column(name="check_list_code")
    private int checkListCode;

    @Column(name="check_list_title")
    private String checkListTitle;

    @Column(name="check_list_flag")
    private Boolean checkListFlag;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public void modify(@Valid EditCheckListInfo editCheckListInfo){

    }
}
