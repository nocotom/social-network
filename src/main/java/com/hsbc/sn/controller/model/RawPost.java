package com.hsbc.sn.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@ApiModel(description = "Represents a model of post")
public class RawPost {

    @NotNull
    @ApiModelProperty(value = "Post id", required = true)
    private Long postId;

    @NotNull
    @Size(min = 1, max = 140)
    @ApiModelProperty(value = "Post message", required = true)
    private String message;

    @NotNull
    @ApiModelProperty(value = "Post owner", required = true)
    private UserId owner;
}
