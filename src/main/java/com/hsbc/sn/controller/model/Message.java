package com.hsbc.sn.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@ApiModel(description = "Represents a model of post message")
public class Message {

    @NotNull
    @Size(min = 1, max = 140)
    @ApiModelProperty(value = "Content of post message", required = true)
    private String message;
}
