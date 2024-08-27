package com.intbyte.wizbuddy.shop.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name = "shop")
@Table(name = "shop")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Shop {

    @Id
    @Column
    private int shopCode;

    @Column
    private String shopName;

    @Column
    private String shopLocation;

    @Column
    private Boolean shopFlag;

    @Column
    private LocalTime shopOpenTime;

    @Column
    private String businessNum;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    // employeeCode -> fk
    @Column
    private Integer employerCode;
}
