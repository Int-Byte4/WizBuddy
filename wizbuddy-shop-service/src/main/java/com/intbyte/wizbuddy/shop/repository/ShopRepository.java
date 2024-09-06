package com.intbyte.wizbuddy.shop.repository;

import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
}