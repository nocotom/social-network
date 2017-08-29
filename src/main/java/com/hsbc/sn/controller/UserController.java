package com.hsbc.sn.controller;

import com.hsbc.sn.controller.entity.*;
import com.hsbc.sn.repository.model.Post;
import com.hsbc.sn.repository.model.User;
import com.hsbc.sn.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
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
        User user = userRepository.findOne(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<RawPost> rawPosts = user.getPosts()
                .stream()
                .map(post -> new RawPost(post.getId(), post.getMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(rawPosts);
    }

    @PostMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseEntity<PostId> addPost(@PathVariable Long userId, @Valid @RequestBody Message message) {
        boolean userExists = userRepository.exists(userId);
        User user;
        if (!userExists) {
            user = new User();
            user.setId(userId);
        } else {
            user = userRepository.findOne(userId);
        }
        Post post = new Post();
        post.setMessage(message.getMessage());
        user.getPosts().add(post);
        userRepository.saveAndFlush(user);

        return new ResponseEntity<>(new PostId(post.getId()), HttpStatus.CREATED);
    }

    @PostMapping(value = "/friendships", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseEntity<Void> addFriendship(@PathVariable Long userId, @Valid @RequestBody UserId friendId) {

        User user = userRepository.findOne(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        User friend = userRepository.findOne(friendId.getUserId());
        if (friend == null) {
            return ResponseEntity.notFound().build();
        }
        user.getFriends().add(friend);
        userRepository.saveAndFlush(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/timeline", consumes = MediaType.ALL_VALUE)
    @Override
    public ResponseEntity<Collection<PersonalizedPost>> timeline(@PathVariable Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<PersonalizedPost> friendsPosts = user
                .getFriends()
                .stream()
                .flatMap(friend -> friend.getPosts().stream())
                .map(post -> new PersonalizedPost(post.getId(), post.getMessage(), new UserId(post.getUser().getId())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(friendsPosts);
    }
}
