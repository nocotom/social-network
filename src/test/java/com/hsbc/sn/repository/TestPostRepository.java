package com.hsbc.sn.repository;

import com.hsbc.sn.repository.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestPostRepository extends JpaRepository<Post, Long> {
}
