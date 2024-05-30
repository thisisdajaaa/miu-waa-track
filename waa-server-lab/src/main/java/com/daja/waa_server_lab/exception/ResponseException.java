package com.daja.waa_server_lab.exception;

import com.daja.waa_server_lab.helper.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseException extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            PostException.NotFoundException.class,
            UserException.NotFoundException.class
    })
    public final ResponseEntity<ResponseHelper.CustomResponse<Object>> handleNotFoundException(RuntimeException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("RuntimeException", exception.getMessage());

        ResponseHelper.CustomResponse<Object> response = new ResponseHelper.CustomResponse<>(false,
                "An error occurred!", null, errors);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
