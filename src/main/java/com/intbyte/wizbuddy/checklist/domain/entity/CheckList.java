package com.intbyte.wizbuddy.checklist.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "checkList")
@Table(name = "checkList")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CheckList {

    @Id
    @Column
    private int checkListCode;

    @Column
    private String checkListTitle;

    @Column
    private Boolean checkListFlag;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

}
