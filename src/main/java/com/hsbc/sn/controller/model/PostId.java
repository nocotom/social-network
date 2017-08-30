package com.hsbc.sn.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel(description = "Represents a model of post id")
public class PostId {

    @NotNull
    @ApiModelProperty(value = "Post id", required = true)
    private Long postId;
}
