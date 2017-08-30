package com.hsbc.sn.controller;

import com.hsbc.sn.controller.model.*;
import com.hsbc.sn.repository.entity.Post;
import com.hsbc.sn.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/users/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController implements UserApi {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/posts", consumes = MediaType.ALL_VALUE)
    @Override
    public ResponseEntity<Collection<RawPost>> getPosts(@PathVariable Long userId) {
        if(!userRepository.usersExists(userId)){
            return ResponseEntity.notFound().build();
        }

        Collection<Post> posts = userRepository.getUserPosts(userId);

        List<RawPost> rawPosts = posts.stream()
                .map(post -> new RawPost(post.getId(), post.getMessage(), new UserId(post.getOwner().getId())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(rawPosts);
    }

    @PostMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseEntity<PostId> addPost(@PathVariable Long userId, @Valid @RequestBody Message message) {
        userRepository.addOrGetUser(userId);

        Post post = new Post();
        post.setMessage(message.getMessage());

        Long postId = userRepository.addPost(userId, post);

        return new ResponseEntity<>(new PostId(postId), HttpStatus.CREATED);
    }

    @PostMapping(value = "/follow", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseEntity<Void> followUser(@PathVariable(value = "userId") Long followerId, @Valid @RequestBody UserId following) {
        if(!userRepository.usersExists(followerId, following.getUserId())){
            return ResponseEntity.notFound().build();
        }

        if(userRepository.userFollowExists(following.getUserId(), followerId)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userRepository.followUser(following.getUserId(), followerId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/timeline", consumes = MediaType.ALL_VALUE)
    @Override
    public ResponseEntity<Collection<RawPost>> timeline(@PathVariable Long userId) {
        if(!userRepository.usersExists(userId)){
            return ResponseEntity.notFound().build();
        }

        List<RawPost> friendsPosts = userRepository.timeline(userId).stream()
                .map(post -> new RawPost(post.getId(), post.getMessage(), new UserId(post.getOwner().getId())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(friendsPosts);
    }
}
