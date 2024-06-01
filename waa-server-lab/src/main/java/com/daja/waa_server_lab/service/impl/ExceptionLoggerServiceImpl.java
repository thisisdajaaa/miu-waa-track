package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.entity.ExceptionLogger;
import com.daja.waa_server_lab.entity.dto.request.ExceptionLoggerDto;
import com.daja.waa_server_lab.repository.IExceptionLoggerRepository;
import com.daja.waa_server_lab.service.spec.IExceptionLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionLoggerServiceImpl implements IExceptionLoggerService {
    private final IExceptionLoggerRepository exceptionLoggerRepository;

    @Autowired
    public ExceptionLoggerServiceImpl(IExceptionLoggerRepository exceptionLoggerRepository) {
        this.exceptionLoggerRepository = exceptionLoggerRepository;
    }

    @Override
    public void add(ExceptionLoggerDto exceptionLoggerDto) {
        ExceptionLogger logger = ExceptionLogger.builder()
                .transactionId(exceptionLoggerDto.getTransactionId())
                .dateTime(exceptionLoggerDto.getDateTime())
                .exceptionType(exceptionLoggerDto.getExceptionType())
                .principle(exceptionLoggerDto.getPrinciple())
                .operation(exceptionLoggerDto.getOperation())
                .build();

        exceptionLoggerRepository.save(logger);
    }
}
