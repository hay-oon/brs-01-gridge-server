package com.brs.gridge.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikePostResponse {
    
    private boolean success;
    private String message;
    @JsonProperty("liked") // 자바의 boolean getter 네이밍 규칙 때문에 isLiked → liked로 자동 변환되므로 @JsonProperty로 명시적으로 지정
    private boolean isLiked;
    private long likeCount;
    
    public static LikePostResponse of(boolean success, String message, boolean isLiked, long likeCount) {
        return new LikePostResponse(success, message, isLiked, likeCount);
    }
}
