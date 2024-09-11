package com.intbyte.wizbuddy.user.command.domain.repository;

import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserEmail(String userEmail);
}
