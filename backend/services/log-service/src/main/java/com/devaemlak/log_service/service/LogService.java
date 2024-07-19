package com.devaemlak.log_service.service;

import com.devaemlak.log_service.converter.LogConverter;
import com.devaemlak.log_service.dto.request.LogSaveRequest;
import com.devaemlak.log_service.dto.request.LogUpdateRequest;
import com.devaemlak.log_service.dto.response.LogResponse;
import com.devaemlak.log_service.exception.ExceptionMessages;
import com.devaemlak.log_service.exception.LogException;
import com.devaemlak.log_service.model.Log;
import com.devaemlak.log_service.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public LogResponse save(LogSaveRequest logSaveRequest) {
        Log log = LogConverter.toLog(logSaveRequest);
        logRepository.save(log);
        return LogConverter.toResponse(log);
    }

    public LogResponse update(LogUpdateRequest request) {
        Optional<Log> foundedLog = logRepository.findById(request.getId());

        if (foundedLog.isPresent()) {
            Log log = foundedLog.get();
            Log updatedLog = LogConverter.toLog(log, request);
            Log saveLog = logRepository.save(updatedLog);
            return LogConverter.toResponse(saveLog);
        }
        log.error(ExceptionMessages.LOG_NOT_FOUND);
        return null;
    }

    public List<LogResponse> getAll() {
        return LogConverter.toResponse(logRepository.findAll());
    }

    public LogResponse getById(String id) {
        Optional<Log> foundedLog = logRepository.findById(id);

        if(foundedLog.isPresent()){
            Log logInstance = foundedLog.get();
            log.info(ExceptionMessages.LOG_FOUND);
            return LogConverter.toResponse(logInstance);
        }
        log.error(ExceptionMessages.LOG_NOT_FOUND);
        return null;
    }

    public LogResponse deleteById(String logId) {
        Log log = logRepository
                .findById(logId)
                .orElseThrow(() -> new LogException(ExceptionMessages.LOG_NOT_FOUND));

        logRepository.deleteById(log.getId());
        return LogConverter.toResponse(log);
    }
}
