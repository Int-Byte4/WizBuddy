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
    public int shopCode;

    @Column
    public String shopName;

    @Column
    public String shopLocation;

    @Column
    public Boolean shopFlag;

    @Column
    public LocalTime shopOpenTime;

    @Column
    public String businessNum;

    @Column
    public LocalDateTime createdAt;

    @Column
    public LocalDateTime updatedAt;

    // employeeCode -> fk
    @Column
    public Integer employerCode;
}
