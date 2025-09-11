package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.vo.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter // @ModelAttribute 사용 시 필수
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchRequest {

    private String username;
    private UserStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    
    // 쿼리를 통해 들어오는 문자열을 LocalDate로 변환하는 setter -> @DateTimeFormat 사용으로 대체 가능
    // public void setStartDate(String startDate) {
    //     this.startDate = startDate != null ? LocalDate.parse(startDate) : null;
    // }
    
    // public void setEndDate(String endDate) {
    //     this.endDate = endDate != null ? LocalDate.parse(endDate) : null;
    // }

}
