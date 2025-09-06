package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequest {

    @NotNull(message = "게시글 ID는 필수입니다")
    private Long postId;
    
    @NotEmpty(message = "내용은 필수입니다")
    @Size(max = 2200, message = "내용은 2200자를 초과할 수 없습니다")
    private String content;

    }
