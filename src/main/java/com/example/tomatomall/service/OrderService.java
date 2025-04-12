package com.example.tomatomall.service;

import com.example.tomatomall.RRVO.ResultOrderPayVO;

public interface OrderService {
    ResultOrderPayVO createOrder(Integer orderId);
    void updateOrderStatus(String orderId, String alipayTradeNo, String amount);
}
