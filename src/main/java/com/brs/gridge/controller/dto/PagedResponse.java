package com.brs.gridge.controller.dto;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    private int pageIndex;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    private boolean hasNext;
    private boolean hasPrevious;
 
    public static <T, R> PagedResponse<R> from(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.getContent().stream() // 현재 페이지의 List<T>를 가져옴 (예: List<Comment>)
                .map(mapper) // 각 요소를 mapper 함수를 사용하여 R 타입으로 변환 (예: Comment -> CommentListResponse)
                .toList();

        return PagedResponse.<R>builder()
                .content(content)
                .pageIndex(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}