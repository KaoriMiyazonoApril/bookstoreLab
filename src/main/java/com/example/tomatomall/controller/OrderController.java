package com.example.tomatomall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.tomatomall.RRVO.ResultOrderPayVO;
import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.OrderDetail;
import com.example.tomatomall.po.Orders;
import com.example.tomatomall.repository.OrderDetailRepository;
import com.example.tomatomall.repository.OrderRepository;
import com.example.tomatomall.service.OrderService;
import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Value("${alipay.alipay-public-key}")
    private String ALI_PUBLIC_KEY;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    //发起支付
    @PostMapping("/{orderId}/pay")
    public Response<ResultOrderPayVO> payOrder(@PathVariable Integer orderId) {
        return Response.buildSuccess(orderService.createOrder(orderId));
    }

    //支付回调
    @PostMapping("/notify")
    public String handleAlipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        // 1. 解析支付宝回调参数（通常是 application/x-www-form-urlencoded）
        Map<String, String> params = request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

        // 2. 验证支付宝签名（防止伪造请求）
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALI_PUBLIC_KEY, "UTF-8", "RSA2");
        if (!signVerified) {
            response.getWriter().print("fail"); // 签名验证失败，返回 fail
            return "fail";
        }

        // 3. 处理业务逻辑（更新订单、减库存等）
        String tradeStatus = params.get("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            String orderId = params.get("out_trade_no"); // 您的订单号
            String alipayTradeNo = params.get("trade_no"); // 支付宝交易号
            String amount = params.get("total_amount"); // 支付金额

            // 更新订单状态（注意幂等性，防止重复处理）
            orderService.updateOrderStatus(orderId, alipayTradeNo, amount);
            // 减库存
            Orders order = orderRepository.findById(Integer.valueOf(orderId))
                    .orElseThrow(TomatoMallException::OrderNotFound);
            if (!"PENDING".equals(order.getStatus())) {
                return "success"; // 如果订单已经处理过，直接返回success
            }
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
            for (OrderDetail detail : orderDetails) {
                productService.reduceStock(detail.getProduct().getId(), detail.getCarts().getQuantity());
            }
        }

        String rdsToken = params.get("rdsToken");
        if (rdsToken != null&&!rdsToken.isEmpty()) {
            request.getSession().setAttribute("rdsToken", rdsToken);
        }

        // 4. 必须返回纯文本的 "success"（支付宝要求）
        response.getWriter().print("success");
        return "success";/*
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, ALI_PUBLIC_KEY, "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过 可做自己需要的操作
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
            }
        }
        return "success";*/
    }
    @GetMapping("/returnUrl")
    public String returnUrl() {
        return "支付成功了";
    }

}
