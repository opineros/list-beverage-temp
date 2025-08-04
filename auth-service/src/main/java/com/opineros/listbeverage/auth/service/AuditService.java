package com.opineros.listbeverage.auth.service;

import com.opineros.listbeverage.auth.model.AuditLog;
import com.opineros.listbeverage.auth.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class AuditService {
    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(HttpServletRequest request) {
        String username = null;
        if (request.getUserPrincipal() != null) {
            username = request.getUserPrincipal().getName();
        }
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setPath(request.getRequestURI());
        log.setMethod(request.getMethod());
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}
