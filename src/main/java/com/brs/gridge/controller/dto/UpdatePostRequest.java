package com.brs.gridge.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostRequest {

    @NotEmpty(message = "게시글 내용은 필수입니다")
    @Size(max = 2200, message = "게시글 내용은 2200자를 초과할 수 없습니다")
    private String content;

    @Size(max = 100, message = "장소명은 100자를 초과할 수 없습니다")
    private String placeName;

    private List<String> attachmentUrls;
}
