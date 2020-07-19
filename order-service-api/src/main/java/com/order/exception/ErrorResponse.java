package com.order.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private Object result;

    @JsonProperty("statusCode")
    private Integer statusCode;
}