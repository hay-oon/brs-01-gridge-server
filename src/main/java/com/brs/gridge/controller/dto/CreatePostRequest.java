package com.brs.gridge.controller.dto;

import com.brs.gridge.common.Constants;
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
    @Size(max = Constants.DatabaseLimits.CONTENT_MAX_LENGTH, message = "내용은 " + Constants.DatabaseLimits.CONTENT_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String content;

    @Size(max = Constants.DatabaseLimits.PLACE_NAME_MAX_LENGTH, message = "장소 이름은 " + Constants.DatabaseLimits.PLACE_NAME_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String placeName;

    @NotEmpty(message = "첨부파일은 필수입니다")
    @Size(min = Constants.File.MIN_ATTACHMENT_COUNT, max = Constants.File.MAX_ATTACHMENT_COUNT, message = "첨부파일은 " + Constants.File.MIN_ATTACHMENT_COUNT + "개 이상 " + Constants.File.MAX_ATTACHMENT_COUNT + "개 이하여야 합니다")
    private List<@Pattern(regexp = Constants.File.URL_PATTERN, message = "올바른 URL 형식이 아닙니다") String> attachmentUrls;

    }
