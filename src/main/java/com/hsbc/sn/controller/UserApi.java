package com.hsbc.sn.controller;

import com.hsbc.sn.model.Post;
import com.hsbc.sn.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;

@Api
@RequestMapping(path = "api/users/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserApi {

    @ApiOperation(value = "Gets all posts produced by specified user", response = Post.class)
    @GetMapping(value = "/posts")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Posts has been retrieved successfully"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Collection<Post>> getPosts(@PathVariable Long userId);

    @ApiOperation(value = "Adds a post to social network", response = Long.class, code = HttpServletResponse.SC_CREATED)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/posts")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Post has been added successfully", response = Long.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Long> addPost(@PathVariable Long userId, @Valid @RequestBody Post post);

    @ApiOperation(value = "Adds a friendship", code = HttpServletResponse.SC_CREATED)
    @PostMapping(value = "/friendships")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Friendship has been added to successfully"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Void> addFriendship(@PathVariable Long userId, @Valid @RequestBody User user);

    @ApiOperation(value = "Gets all posts produced by user's friends")
    @GetMapping(value = "/timeline")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Posts has been retrieved successfully"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Collection<Post>> timeline(@PathVariable Long userId);
}
