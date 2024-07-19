package com.devaemlak.log_service.controller;

import com.devaemlak.log_service.dto.request.LogSaveRequest;
import com.devaemlak.log_service.dto.request.LogUpdateRequest;
import com.devaemlak.log_service.dto.response.GenericResponse;
import com.devaemlak.log_service.dto.response.LogResponse;
import com.devaemlak.log_service.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @PostMapping
    public ResponseEntity<LogResponse> save(@RequestBody LogSaveRequest request) {
        LogResponse logResponse = logService.save(request);
        return new ResponseEntity<>(logResponse, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<LogResponse> update(@RequestBody LogUpdateRequest request) {
        LogResponse logResponse = logService.update(request);
        return new ResponseEntity<>(logResponse, HttpStatus.OK);
    }

    @GetMapping
    public GenericResponse<List<LogResponse>> getAll() {
        return GenericResponse.success(logService.getAll());
    }

    @GetMapping("/{id}")
    public GenericResponse<LogResponse> getById(@PathVariable String id) {
        return GenericResponse.success(logService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LogResponse> deleteById(@PathVariable String id) {
        LogResponse logResponse = logService.deleteById(id);
        return ResponseEntity.ok(logResponse);
    }
}
