package com.example.tomatomall.controller;

import com.alipay.api.AlipayApiException;
import com.example.tomatomall.RRVO.ResultOrderPayVO;
import com.example.tomatomall.service.OrderService;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //发起支付
    @PostMapping("/{orderId}/pay")
    public Response<ResultOrderPayVO> payOrder(@PathVariable Integer orderId)  {
        return Response.buildSuccess(orderService.createOrder(orderId));
    }

    //支付回调
    @PostMapping("/notify")
    public String handleAlipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        System.out.println("支付回调");
        return orderService.handleAlipayNotify(request, response);
    }
    @GetMapping("/returnUrl")
    public String returnUrl() {
        System.out.println("支付成功了");
        return "支付成功了";
    }

}
