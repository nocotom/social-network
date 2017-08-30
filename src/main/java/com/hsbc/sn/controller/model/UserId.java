package com.hsbc.sn.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel(description = "Represents a model of user id")
public class UserId {

    @NotNull
    @ApiModelProperty(value = "User id", required = true)
    private Long userId;
}
