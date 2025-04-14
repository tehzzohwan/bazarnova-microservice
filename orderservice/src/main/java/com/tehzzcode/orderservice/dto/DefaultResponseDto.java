package com.tehzzcode.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponseDto<T> {
    private String statusCode;
    private String statusMessage;
    private T data;
}
