package com.tranning.management.common.response;

import lombok.Data;

@Data
public class DataResponse<T> {
    private Long timestamp;
    private int statusCode;
    private String statusMessage;
    private T data;
}
