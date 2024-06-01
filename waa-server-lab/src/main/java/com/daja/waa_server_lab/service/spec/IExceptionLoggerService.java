package com.daja.waa_server_lab.service.spec;

import com.daja.waa_server_lab.entity.dto.request.ExceptionLoggerDto;

public interface IExceptionLoggerService {
    void add(ExceptionLoggerDto exceptionLoggerDto);
}
