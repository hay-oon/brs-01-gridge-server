package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPostResponse {
    
    private boolean success;
    private String message;
    private boolean isBookmarked;
    private long bookmarkCount;
    
    public static BookmarkPostResponse of(boolean success, String message, boolean isBookmarked, long bookmarkCount) {
        return new BookmarkPostResponse(success, message, isBookmarked, bookmarkCount);
    }
}
