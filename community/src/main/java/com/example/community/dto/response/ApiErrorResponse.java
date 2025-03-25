package com.example.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    private String message;
    private List<String> errors;

    public static ApiErrorResponse of(String message, List<String> errors) {
        return new ApiErrorResponse(message, errors);
    }
}
