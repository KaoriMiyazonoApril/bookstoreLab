package com.example.tomatomall.serviceImpl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;

public class AliPay {
    AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2016072200101XXXX","请复制第1步中生成的密钥中的商家应用私钥","json","utf-8","沙箱环境RSA2支付宝公钥","RSA2");
    AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
    AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
    /*request.setBizModel(model);
    model.setOutTradeNo(System.currentTimeMills());
    model.setTotalAmount("88.88");
    model.setSubject("Iphone6 16G");
    AlipayTradePrecreateResponse response = alipayClient.execute(request);
    System.out.print(response.getBody());
    System.out.print(response.getQrCode());*/

}
