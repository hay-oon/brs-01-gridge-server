package com.brs.gridge.controller.dto;

import com.brs.gridge.common.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequest {
    
    @NotBlank(message = "댓글 내용은 필수입니다")
    @Size(max = Constants.DatabaseLimits.CONTENT_MAX_LENGTH, message = "댓글 내용은 " + Constants.DatabaseLimits.CONTENT_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String content;
}
