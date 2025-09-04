package com.brs.gridge.repository;

import com.brs.gridge.domain.entity.PrivacyAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivacyAgreementRepository extends JpaRepository<PrivacyAgreement, Long> {
}
