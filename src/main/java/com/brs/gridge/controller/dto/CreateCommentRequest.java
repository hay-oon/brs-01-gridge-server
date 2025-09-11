package com.brs.gridge.controller.dto;

import com.brs.gridge.common.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequest {
    
    @NotEmpty(message = "내용은 필수입니다")
    @Size(max = Constants.DatabaseLimits.CONTENT_MAX_LENGTH, message = "내용은 " + Constants.DatabaseLimits.CONTENT_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String content;

    }
