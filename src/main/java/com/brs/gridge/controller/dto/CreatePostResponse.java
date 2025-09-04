package com.brs.gridge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostResponse {

    private final String message;
    private final boolean success;

    public static CreatePostResponse of(boolean success, String message) {
        return new CreatePostResponse(message, success);
    }
}
