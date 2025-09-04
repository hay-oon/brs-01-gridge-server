package com.brs.gridge.controller.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostRequest {

    @NotEmpty(message = "내용은 필수입니다")
    @Size(max = 2200, message = "내용은 2200자를 초과할 수 없습니다")
    private String content;

    @Size(max = 100, message = "장소 이름은 100자를 초과할 수 없습니다")
    private String placeName;

    @NotEmpty(message = "첨부파일은 필수입니다")
    @Size(min = 1, max = 10, message = "첨부파일은 1개 이상 10개 이하여야 합니다")
    private List<@Pattern(regexp = "^https?://.*", message = "올바른 URL 형식이 아닙니다") String> attachmentUrls;

    }
