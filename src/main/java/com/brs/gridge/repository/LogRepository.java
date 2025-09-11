package com.brs.gridge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brs.gridge.domain.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    
    Page<Log> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Log> findByEntityType(String entityType, Pageable pageable);
    
}
