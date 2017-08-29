package com.hsbc.sn.repository;

import com.hsbc.sn.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
