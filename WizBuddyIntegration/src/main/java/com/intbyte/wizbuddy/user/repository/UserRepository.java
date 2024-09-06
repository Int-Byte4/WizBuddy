package com.intbyte.wizbuddy.user.repository;

import com.intbyte.wizbuddy.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserEmail(String userEmail);

    User findByUserCode(String employerCode);
}
