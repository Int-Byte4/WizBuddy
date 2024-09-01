package com.intbyte.wizbuddy.shop.domain.entity;

import com.intbyte.wizbuddy.shop.domain.DeleteShopInfo;
import com.intbyte.wizbuddy.shop.domain.EditShopInfo;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name = "shop")
@Table(name = "shop")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int shopCode;

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String shopLocation;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean shopFlag;

    @Column(nullable = false)
    private LocalTime shopOpenTime;

    @Column(nullable = false)
    private String businessNum;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String employerCode;

    public void modify(@Valid EditShopInfo editShopInfo) {
        this.shopName = editShopInfo.getShopName();
        this.shopLocation = editShopInfo.getShopLocation();
        this.shopOpenTime = editShopInfo.getShopOpenTime();
        this.updatedAt = editShopInfo.getUpdatedAt();
    }

    public void removeRequest(@Valid DeleteShopInfo deleteShopInfo) {
        this.shopCode = deleteShopInfo.getShopCode();
        this.employerCode = deleteShopInfo.getEmployerCode();
        this.shopFlag = deleteShopInfo.isShopFlag();
        this.updatedAt = deleteShopInfo.getUpdatedAt();
    }
}
