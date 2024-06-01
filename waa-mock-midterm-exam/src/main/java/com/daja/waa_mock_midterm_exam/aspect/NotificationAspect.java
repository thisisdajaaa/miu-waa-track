package com.daja.waa_mock_midterm_exam.aspect;

import com.daja.waa_mock_midterm_exam.entity.dto.response.StudentDetailDto;
import com.daja.waa_mock_midterm_exam.helper.ResponseHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class NotificationAspect {

    @AfterReturning(pointcut = "execution(* com.daja.waa_mock_midterm_exam.controller.StudentController.*(..))", returning = "result")
    public void sendAlert(JoinPoint joinPoint, Object result) {
        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();

            if (body instanceof ResponseHelper.CustomResponse<?> customResponse) {
                Object data = customResponse.getData();

                if (data instanceof List<?> resultList) {
                    System.out.println("Is result a List?: true");
                    System.out.println("Result is a list of size: " + resultList.size());

                    if (!resultList.isEmpty()) {
                        System.out.println("First element class: " + resultList.get(0).getClass());

                        if (resultList.getFirst() instanceof StudentDetailDto) {
                            System.out.println("Alert: A method returning a list of students was called.");
                        }
                    }
                } else {
                    System.out.println("Is result a List?: false");
                }
            }
        }
    }
}
