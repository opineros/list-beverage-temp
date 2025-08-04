package com.opineros.listbeverage.auth.repository;

import com.opineros.listbeverage.auth.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
