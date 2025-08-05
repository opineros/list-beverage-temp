package com.opineros.listbeverage.auth.repository;

import com.opineros.listbeverage.auth.model.AuditLog;
import org.springframework.data.repository.CrudRepository;

public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {
}
