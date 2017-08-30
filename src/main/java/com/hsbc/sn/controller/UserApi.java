package com.hsbc.sn.controller;

import com.hsbc.sn.controller.model.*;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Api
public interface UserApi {

    @ApiOperation(value = "Gets all posts produced by specified user", response = RawPost.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Posts has been retrieved successfully", response = RawPost.class, responseContainer = "List"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Collection<RawPost>> getPosts(@ApiParam(value = "Owner id") Long userId);

    @ApiOperation(value = "Adds a post to social network. If a user does not exist, it will be created", response = PostId.class, code = HttpServletResponse.SC_CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Post has been added successfully", response = PostId.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<PostId> addPost(@ApiParam(value = "Owner id") Long userId,
                                   Message message);

    @ApiOperation(value = "Follows posts produced by specified user", code = HttpServletResponse.SC_CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Following has been registered successfully"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Follower or following user not found"),
            @ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Following the user is already registered"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Void> followUser(@ApiParam(value = "Follower") Long followerId,
                                    UserId following);

    @ApiOperation(value = "Gets all posts produced by following users", response = RawPost.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Posts has been retrieved successfully", response = RawPost.class, responseContainer = "List"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error")})
    ResponseEntity<Collection<RawPost>> timeline(@ApiParam(value = "Follower") Long userId);
}
