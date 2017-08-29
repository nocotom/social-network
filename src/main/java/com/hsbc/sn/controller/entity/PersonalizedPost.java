package com.hsbc.sn.controller.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class PersonalizedPost {

    @NotNull
    private Long postId;

    @NotNull
    @Size(min = 1, max = 140)
    private String message;

    @NotNull
    private UserId user;
}
