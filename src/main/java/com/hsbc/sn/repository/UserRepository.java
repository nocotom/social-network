package com.hsbc.sn.repository;

import com.hsbc.sn.repository.entity.Post;
import com.hsbc.sn.repository.entity.User;

import java.util.Collection;

public interface UserRepository {

    User addOrGetUser(Long userId);

    boolean usersExists(Long... usersId);

    Long addPost(Long userId, Post post);

    Collection<Post> getUserPosts(Long userId);

    void followUser(Long followingId, Long followerId);

    Collection<Post> timeline(Long userId);
}
