package com.brs.gridge.repository;

import com.brs.gridge.domain.entity.PrivacyAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrivacyAgreementRepository extends JpaRepository<PrivacyAgreement, Long> {
    
    // 만료된 개인정보처리방침 동의 조회
    @Query("SELECT pa FROM PrivacyAgreement pa WHERE pa.expiredAt <= :currentTime")
    List<PrivacyAgreement> findExpiredAgreements(@Param("currentTime") LocalDateTime currentTime);
    
}
