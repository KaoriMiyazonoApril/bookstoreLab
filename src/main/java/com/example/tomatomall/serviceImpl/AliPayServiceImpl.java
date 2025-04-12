package com.example.tomatomall.serviceImpl;

import com.example.tomatomall.service.AliPayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AliPayServiceImpl implements AliPayService {
    @Value("${alipay.server-url}")
    private String serverUrl;

    @Value("${alipay.return-url}")
    private String returnUrl;

    @Value("${alipay.notify-url}")
    private String notifyUrl;

    @Override
    public String createPaymentForm(String orderId, Double totalAmount, String subject) {
        // 这里实现具体的支付宝支付表单生成逻辑
        // 可以使用支付宝SDK或直接构建HTML表单
        // 返回生成的支付表单HTML字符串
        return "<form action='" + serverUrl + "' method='POST'>" +
                "<input type='hidden' name='orderId' value='" + orderId + "'>" +
                "<input type='hidden' name='totalAmount' value='" + totalAmount + "'>" +
                "<input type='hidden' name='subject' value='" + subject + "'>" +
                "<input type='hidden' name='returnUrl' value='" + returnUrl + "'>" +
                "<input type='hidden' name='notifyUrl' value='" + notifyUrl + "'>" +
                "</form>";
    }
}
