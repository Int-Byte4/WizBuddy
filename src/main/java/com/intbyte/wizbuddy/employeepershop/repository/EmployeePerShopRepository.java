package com.intbyte.wizbuddy.employeepershop.repository;

import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShopId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePerShopRepository extends JpaRepository<EmployeePerShop, EmployeePerShopId> {
}
