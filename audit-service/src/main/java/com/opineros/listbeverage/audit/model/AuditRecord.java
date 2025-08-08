package com.opineros.listbeverage.audit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "audit_records")
public class AuditRecord {
    @Id
    private String id;
    private String serviceName;
    private String eventType;
    private Map<String, Object> payload;
    private Instant timestamp;

    public AuditRecord() { }

    public AuditRecord(String serviceName, String eventType, Map<String, Object> payload, Instant timestamp) {
        this.serviceName = serviceName;
        this.eventType = eventType;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Map<String, Object> getPayload() { return payload; }
    public void setPayload(Map<String, Object> payload) { this.payload = payload; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
