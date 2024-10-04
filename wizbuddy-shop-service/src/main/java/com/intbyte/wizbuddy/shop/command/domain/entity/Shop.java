package com.intbyte.wizbuddy.shop.command.domain.entity;

import com.intbyte.wizbuddy.shop.command.application.dto.RequestDeleteShopDTO;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestEditShopDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name = "shop")
@Table(name = "shop")
@Getter @Setter
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

    public void modify(@Valid RequestEditShopDTO requestEditShopDTO) {
        this.shopName = requestEditShopDTO.getShopName();
        this.shopLocation = requestEditShopDTO.getShopLocation();
        this.shopOpenTime = requestEditShopDTO.getShopOpenTime();
    }

    public void removeRequest(@Valid RequestDeleteShopDTO requestDeleteShopDTO) {
        this.shopCode = requestDeleteShopDTO.getShopCode();
        this.employerCode = requestDeleteShopDTO.getEmployerCode();
    }
}
