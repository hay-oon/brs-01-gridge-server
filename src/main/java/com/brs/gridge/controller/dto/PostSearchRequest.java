package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.vo.PostStatus;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter // @ModelAttribute 사용 시 필수
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSearchRequest {

    private String username;
    private PostStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdDate;

}
