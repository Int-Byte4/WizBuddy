package com.intbyte.wizbuddy.employeepershop.command.domain;

import com.intbyte.wizbuddy.employeepershop.command.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.command.domain.entity.EmployeePerShopId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePerShopRepository extends JpaRepository<EmployeePerShop, EmployeePerShopId> {
}
