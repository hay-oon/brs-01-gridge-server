package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentResponse {
    
    private boolean success;
    private String message;
    
    public static UpdateCommentResponse of(boolean success, String message) {
        return new UpdateCommentResponse(success, message);
    }
}
