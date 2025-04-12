package com.example.tomatomall.RRVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//用于返回支付请求的结果
@Getter
@Setter
@NoArgsConstructor
public class CheckoutResultVO {
    private String orderId;
    private String username;
    private Double totalAmount;
    private String paymentMethod;
    private String createTime;
    private String status;
}
