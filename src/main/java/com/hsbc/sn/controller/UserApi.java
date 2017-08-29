package com.hsbc.sn.controller;

import com.hsbc.sn.controller.entity.*;
import com.hsbc.sn.repository.model.Post;
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

    @ApiOperation(value = "Gets all posts produced by specified user", response = RawPost[].class)
    @GetMapping(value = "/posts")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Posts has been retrieved successfully"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Collection<RawPost>> getPosts(@PathVariable Long userId);

    @ApiOperation(value = "Adds a post to social network. If a user does not exist, it will be created", response = Long.class, code = HttpServletResponse.SC_CREATED)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/posts")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Post has been added successfully", response = PostId.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<PostId> addPost(@PathVariable Long userId, @Valid @RequestBody Message message);

    @ApiOperation(value = "Adds a friendship", code = HttpServletResponse.SC_CREATED)
    @PostMapping(value = "/friendships")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Friendship has been added to successfully"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Void> addFriendship(@PathVariable Long userId, @Valid @RequestBody UserId friendId);

    @ApiOperation(value = "Gets all posts produced by user's friends", response = PersonalizedPost[].class)
    @GetMapping(value = "/timeline")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Posts has been retrieved successfully"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Collection<PersonalizedPost>> timeline(@PathVariable Long userId);
}
