package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.entity.Logger;
import com.daja.waa_server_lab.entity.dto.request.LoggerDto;
import com.daja.waa_server_lab.repository.ILoggerRepository;
import com.daja.waa_server_lab.service.spec.ILoggerService;
import org.springframework.stereotype.Service;

@Service
public class LoggerServiceImpl implements ILoggerService {
    private final ILoggerRepository loggerRepository;

    public LoggerServiceImpl(ILoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    @Override
    public void add(LoggerDto loggerDto) {
        Logger logger = Logger.builder()
                .transactionId(loggerDto.getTransactionId())
                .dateTime(loggerDto.getDateTime())
                .principle(loggerDto.getPrinciple())
                .operation(loggerDto.getOperation())
                .build();

        loggerRepository.save(logger);
    }
}
