package com.opineros.listbeverage.shared.audit;

import java.time.Instant;
import java.util.Map;

public class AuditEvent {
    private String serviceName;
    private String eventType;
    private Map<String, Object> payload;
    private Instant timestamp;

    public AuditEvent() {
        this.timestamp = Instant.now();
    }

    public AuditEvent(String serviceName, String eventType, Map<String, Object> payload) {
        this.serviceName = serviceName;
        this.eventType = eventType;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
