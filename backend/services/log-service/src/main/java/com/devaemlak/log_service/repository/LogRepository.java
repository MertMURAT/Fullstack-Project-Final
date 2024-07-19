package com.devaemlak.log_service.repository;

import com.devaemlak.log_service.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String> {
}
