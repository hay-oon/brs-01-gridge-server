package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.vo.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionSearchRequest {
    
    private String userId;           
    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;     
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;   

    private SubscriptionStatus status; 
    
}
