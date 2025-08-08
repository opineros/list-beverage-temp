package com.opineros.listbeverage.audit.repository;

import com.opineros.listbeverage.audit.model.AuditRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends MongoRepository<AuditRecord, String> {
}
