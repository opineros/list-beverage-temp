package com.opineros.listbeverage.gateway.service;

import com.opineros.listbeverage.shared.audit.AuditEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuditPublisher {

    private final StreamBridge bridge;
    private final String serviceName;

    public AuditPublisher(StreamBridge bridge, @Value("${spring.application.name}") String serviceName) {
        this.bridge      = bridge;
        this.serviceName = serviceName;
    }

    public void publish(String eventType, Map<String,Object> payload)
    {
        AuditEvent evt = new AuditEvent(serviceName, eventType, payload);
        bridge.send("auditEventOut-out-0", evt);
    }
}