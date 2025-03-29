package com.example.tomatomall.exception;
import com.example.tomatomall.vo.Response;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Response<String> handleException(Exception e) {
        e.printStackTrace();
        return Response.buildFailure("服务器内部错误，请联系管理员", "500");
    }

    @ExceptionHandler(value = TomatoMallException.class)
    public Response<String> handleAIExternalException(TomatoMallException e) {
        e.printStackTrace();
        // 根据异常类型返回更具体的错误信息
        String message = e.getMessage();
        String code = "400";

        if (message.contains("用户名")) {
            message = "用户操作失败：" + message;
        } else if (message.contains("商品")) {
            message = "商品操作失败：" + message;
            code = "404";
        } else if (message.contains("格式")) {
            message = "数据格式错误：" + message;
        }

        // 删除错误的返回语句
        // return Response.buildFailure("请求参数验证失败：" + errorMessage, "400");

        return Response.buildFailure(message, code);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Response.buildFailure(errorMessage, "400");
    }
}