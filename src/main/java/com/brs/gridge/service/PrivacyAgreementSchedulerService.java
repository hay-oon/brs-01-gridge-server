package com.brs.gridge.service;

import com.brs.gridge.domain.entity.PrivacyAgreement;
import com.brs.gridge.repository.PrivacyAgreementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivacyAgreementSchedulerService {
    
    private final PrivacyAgreementRepository privacyAgreementRepository;
    
    @Scheduled(cron = "0 0 9 * * *")// 매일 오전 9시
    @Transactional
    public void checkExpiredPrivacyAgreements() {
        log.info("개인정보처리방침 동의 만료 체크 스케줄러 시작");
        
        LocalDateTime currentTime = LocalDateTime.now();
        List<PrivacyAgreement> expiredAgreements = privacyAgreementRepository.findExpiredAgreements(currentTime);
        
        if (expiredAgreements.isEmpty()) {
            log.info("만료된 개인정보처리방침 동의가 없습니다.");
            return;
        }
        
        log.info("만료된 개인정보처리방침 동의 {}건 발견", expiredAgreements.size());
        
        for (PrivacyAgreement agreement : expiredAgreements) {
            try {
                processExpiredAgreement(agreement);
            } catch (Exception e) {
        log.error("개인정보처리방침 동의 처리 중 오류 발생 - 사용자 ID:" + agreement.getUser().getUserId() + " 오류:" + e.getMessage(), e);
            }   
        }
        
        log.info("개인정보처리방침 동의 만료 체크 스케줄러 완료");
    }
    
    private void processExpiredAgreement(PrivacyAgreement agreement) {
        log.info("만료된 개인정보처리방침 동의 처리 - 사용자:" + agreement.getUser().getUsername() + " 동의일:" + agreement.getAgreedAt() + " 만료일:" + agreement.getExpiredAt());
        
        // TODO: 실제 구현 시 고려할만한 작업
        // 1. 사용자에게 재동의 요청 알림 발송
        // 2. 일정 기간 후에도 재동의하지 않으면 계정 비활성화
        // 3. 관리자에게 알림 발송

        // 현재는 로그만 출력
        log.warn("사용자 " + agreement.getUser().getUsername() + "의 개인정보처리방침 동의가 만료되었습니다. 재동의가 필요합니다.");
    }
    
}
