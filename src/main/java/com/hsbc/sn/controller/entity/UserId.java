package com.hsbc.sn.controller.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserId {

    @NotNull
    private Long userId;
}
