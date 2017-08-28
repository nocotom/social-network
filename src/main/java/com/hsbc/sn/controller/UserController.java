package com.hsbc.sn.controller;

import com.hsbc.sn.model.Post;
import com.hsbc.sn.model.User;
import com.hsbc.sn.repository.UserRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/users/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController implements UserApi {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/posts")
    @Override
    public ResponseEntity<Collection<Post>> getPosts(@PathVariable Long userId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/posts")
    @Override
    public ResponseEntity<Long> addPost(@PathVariable Long userId, @Valid @RequestBody Post post) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/friendships")
    @Override
    public ResponseEntity<Void> addFriendship(@PathVariable Long userId, @Valid @RequestBody User friend) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/timeline")
    @Override
    public ResponseEntity<Collection<Post>> timeline(@PathVariable Long userId) {
        return ResponseEntity.ok().build();

    }
}
