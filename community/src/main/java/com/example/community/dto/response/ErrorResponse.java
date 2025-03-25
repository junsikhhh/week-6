package com.example.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> errors;

    public static ErrorResponse of(String message, List<String> errors) {
        return new ErrorResponse(message, errors);
    }
}
