package com.hsbc.sn.repository;

import com.hsbc.sn.repository.entity.Follow;
import com.hsbc.sn.repository.entity.FollowId;
import com.hsbc.sn.repository.entity.Post;
import com.hsbc.sn.repository.entity.User;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public User addOrGetUser(Long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);
        query.where(builder.equal(userRoot.get("id"), userId));

        List<User> users = entityManager.createQuery(query).getResultList();
        if (users.size() > 0) {
            return users.get(0);
        }

        User user = new User();
        user.setId(userId);
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    @Override
    public boolean usersExists(Long... usersId) {
        if(usersId.length == 0){
            return true;
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> userRoot = query.from(User.class);
        Predicate[] predicates = new Predicate[usersId.length];
        for(int i = 0; i< usersId.length; i++){
            predicates[i] = builder.equal(userRoot.get("id"), usersId[i]);
        }
        query.select(builder.count(userRoot));
        query.where(builder.or(predicates));

        Long size = entityManager.createQuery(query).getSingleResult();
        return size > 0;
    }

    @Transactional
    @Override
    public Long addPost(Long userId, Post post) {
        User user = new User();
        user.setId(userId);
        post.setOwner(user);
        entityManager.persist(post);
        entityManager.flush();
        return post.getId();
    }

    @Override
    public Collection<Post> getUserPosts(Long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root<Post> postRoot = query.from(Post.class);

        query.where(builder.equal(postRoot.get("owner"), userId));
        query.orderBy(builder.desc(postRoot.get("createdAt")));

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public void followUser(Long followingId, Long followerId) {
        User follower = new User();
        follower.setId(followerId);

        User following = new User();
        following.setId(followingId);

        FollowId followId = new FollowId();
        followId.setFollower(follower);
        followId.setFollowing(following);

        Follow follow = new Follow();
        follow.setId(followId);

        entityManager.persist(follow);
        entityManager.flush();
    }

    @Override
    public Collection<Post> timeline(Long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root<Post> postRoot = query.from(Post.class);
        Root<Follow> followRoot = query.from(Follow.class);

        query.select(postRoot);
        query.where(builder.and(
                builder.equal(postRoot.get("owner"), followRoot.get("id").get("following")),
                builder.equal(followRoot.get("id").get("follower"), userId)
        ));
        query.orderBy(builder.desc(postRoot.get("createdAt")));

        return entityManager.createQuery(query).getResultList();
    }
}
