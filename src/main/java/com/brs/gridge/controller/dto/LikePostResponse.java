package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikePostResponse {
    
    private boolean success;
    private String message;
    private boolean isLiked;
    private long likeCount;
    
    public static LikePostResponse of(boolean success, String message, boolean isLiked, long likeCount) {
        return new LikePostResponse(success, message, isLiked, likeCount);
    }
}
