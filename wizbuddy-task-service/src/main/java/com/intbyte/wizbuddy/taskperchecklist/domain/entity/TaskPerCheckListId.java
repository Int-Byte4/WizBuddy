package com.intbyte.wizbuddy.taskperchecklist.domain.entity;

import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TaskPerCheckListId implements Serializable {

    private Integer checkListCode;
    private Integer taskCode;
}
