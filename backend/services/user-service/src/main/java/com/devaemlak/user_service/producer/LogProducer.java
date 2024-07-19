package com.devaemlak.user_service.producer;

import com.devaemlak.user_service.config.RabbitConfig;
import com.devaemlak.user_service.exception.ExceptionMessages;
import com.devaemlak.user_service.producer.dto.LogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogProducer {

    private final AmqpTemplate rabbitTemplate;
    private final RabbitConfig rabbitConfig;

    public void sendToLog(LogDto logDto){
        rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getRoutingKey(), logDto);
        log.info(ExceptionMessages.LOG_WRITE_QUEUE +  " : {} ", rabbitConfig.getExchange());
    }
}