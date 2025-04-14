package com.tehzzcode.orderservice.dto;

public class DefaultResponse<T> {
    private String status;
    private String message;
    private T data;
}
