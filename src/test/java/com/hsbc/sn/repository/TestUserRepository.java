package com.hsbc.sn.repository;

import com.hsbc.sn.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestUserRepository extends JpaRepository<User, Long> {
}
