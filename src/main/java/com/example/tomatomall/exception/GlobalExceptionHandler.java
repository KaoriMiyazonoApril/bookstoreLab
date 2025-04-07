package com.example.tomatomall.exception;
import com.example.tomatomall.vo.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({TomatoMallException.class,NoSuchElementException.class, IOException.class} )
    public Response<String> handleAIExternalException(TomatoMallException e) {
        e.printStackTrace();
        return Response.buildFailure(e.getMessage(), "400");
    }

}