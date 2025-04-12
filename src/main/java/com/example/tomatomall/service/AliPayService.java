package com.example.tomatomall.service;

public interface AliPayService {
    String createPaymentForm(String orderId, Double totalAmount, String subject);
}
