package com.intbyte.wizbuddy.taskperchecklist.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TaskPerChecklistId implements Serializable {

    private Integer checkListCode;
    private Integer taskCode;
}
