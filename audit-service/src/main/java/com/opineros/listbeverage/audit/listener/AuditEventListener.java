package com.opineros.listbeverage.audit.listener;

import com.opineros.listbeverage.audit.model.AuditRecord;
import com.opineros.listbeverage.audit.repository.AuditRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class AuditEventListener {

    private final AuditRepository repository;

    public AuditEventListener(AuditRepository repository) {
        this.repository = repository;
    }

    @Bean
    public Consumer<Map<String, Object>> auditEventIn() {
        return event -> {
            String serviceName = (String) event.get("serviceName");
            String eventType = (String) event.get("eventType");
            Map<String, Object> payload = (Map<String, Object>) event.get("payload");
            AuditRecord record = new AuditRecord(serviceName, eventType, payload, Instant.now());
            repository.save(record);
        };
    }
}
