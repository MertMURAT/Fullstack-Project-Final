package com.devaemlak.log_service.consumer;

import com.devaemlak.log_service.consumer.response.LogDto;
import com.devaemlak.log_service.converter.LogConverter;
import com.devaemlak.log_service.exception.ExceptionMessages;
import com.devaemlak.log_service.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LogConsumer {

    private final LogRepository logRepository;

    @RabbitListener(queues = "${rabbitmq.logs.queue}")
    public void fetchLogAndSaveToMongoDB(LogDto logDto) {
        log.info(ExceptionMessages.LOG_READ_QUEUE + " : {}", logDto);
        logRepository.save(LogConverter.toLog(logDto));
    }
}
