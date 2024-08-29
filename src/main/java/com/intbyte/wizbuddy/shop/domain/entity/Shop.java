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
    @Column(name = "shop_code")
    private int shopCode;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "shop_location")
    private String shopLocation;

    @Column(name = "shop_flag")
    @ColumnDefault("true")
    private Boolean shopFlag;

    @Column(name = "shop_open_time")
    private LocalTime shopOpenTime;

    @Column(name = "business_num")
    private String businessNum;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "employer_code")
    private Integer employerCode;

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
    }
}
