package com.example.tomatomall.exception;
import com.example.tomatomall.vo.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({TomatoMallException.class,NoSuchElementException.class, IOException.class} )
    public Response<String> handleAIExternalException(TomatoMallException e) {
        e.printStackTrace();
        if(Objects.equals(e.getMessage(), "未登录，请先登录"))
            return Response.buildFailure(e.getMessage(), "401");
        return Response.buildFailure(e.getMessage(), "400");
    }

}