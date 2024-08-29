package com.intbyte.wizbuddy.shop.repository;

import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
}