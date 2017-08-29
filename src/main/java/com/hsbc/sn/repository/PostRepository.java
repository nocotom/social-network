package com.hsbc.sn.repository;

import com.hsbc.sn.repository.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
