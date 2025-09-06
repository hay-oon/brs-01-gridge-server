package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPostResponse {
    
    private boolean success;
    private String message;
    @JsonProperty("bookmarked") // 자바의 boolean getter 네이밍 규칙 때문에 isBookmarked → bookmarked로 자동 변환되므로 @JsonProperty로 명시적으로 지정
    private boolean isBookmarked;
    private long bookmarkCount;
    
    public static BookmarkPostResponse of(boolean success, String message, boolean isBookmarked, long bookmarkCount) {
        return new BookmarkPostResponse(success, message, isBookmarked, bookmarkCount);
    }
}
