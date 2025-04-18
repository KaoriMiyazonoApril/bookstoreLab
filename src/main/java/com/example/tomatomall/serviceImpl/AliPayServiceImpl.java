package com.example.tomatomall.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.tomatomall.service.AliPayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@Service
public class AliPayServiceImpl implements AliPayService {
    @Value("${alipay.app-id}")
    private String appId;
    @Value("${alipay.private-key}")
    private String privateKey;
    @Value("${alipay.alipay-public-key}")
    private String alipayPublicKey;
    @Value("${alipay.server-url}")
    private String serverUrl;
    @Value("${alipay.charset}")
    private String charset;
    @Value("${alipay.sign-type}")
    private String signType;
    @Value("${alipay.notify-url}")
    private String notifyUrl;
    @Value("${alipay.return-url}")
    private String returnUrl;
    private static final String FORMAT = "JSON";

    @Override
    public String createPaymentForm(String orderId, Double totalAmount, String subject) {
        // 创建AlipayClient

        AlipayClient alipayClient = new DefaultAlipayClient(
            serverUrl,
            appId,
            privateKey,
            "json",
            "UTF-8",
            alipayPublicKey,
            "RSA2"
        );

        // 创建请求对象
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);

        // 构建业务参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizContent.put("total_amount", totalAmount);
        bizContent.put("subject", subject);
        request.setBizContent(bizContent.toJSONString());

        // 生成支付表单
        try {
            return alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException("生成支付表单失败", e);
        }
        /*

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId,
                privateKey, FORMAT, charset, alipayPublicKey, signType);
        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);  // 我们自己生成的订单编号
        bizContent.put("total_amount", totalAmount); // 订单的总金额
        bizContent.put("subject", subject);   // 支付的名称
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");// 固定配置
        request.setBizContent(bizContent.toString());
        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;*/
    }
}
