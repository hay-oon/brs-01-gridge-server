package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCommentResponse {
    
    private boolean success;
    private String message;
    
    public static DeleteCommentResponse of(boolean success, String message) {
        return new DeleteCommentResponse(success, message);
    }
}
