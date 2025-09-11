package com.brs.gridge.controller.dto;

import com.brs.gridge.common.Constants;
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
    @Size(max = Constants.DatabaseLimits.CONTENT_MAX_LENGTH, message = "게시글 내용은 " + Constants.DatabaseLimits.CONTENT_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String content;

    @Size(max = Constants.DatabaseLimits.PLACE_NAME_MAX_LENGTH, message = "장소명은 " + Constants.DatabaseLimits.PLACE_NAME_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String placeName;

    private List<String> attachmentUrls;
}
