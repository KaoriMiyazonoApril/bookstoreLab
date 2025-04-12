package com.example.tomatomall.serviceImpl;

import com.example.tomatomall.RRVO.ResultOrderPayVO;
import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Orders;
import com.example.tomatomall.repository.OrderRepository;
import com.example.tomatomall.service.AliPayService;
import com.example.tomatomall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AliPayService aliPayService;

    @Override
    public ResultOrderPayVO createOrder(Integer orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(TomatoMallException::OrderNotFound);

        if(!"PENDING".equals(order.getStatus())){
            throw TomatoMallException.OrderPayStatusError();
        }
        // 2. 调用支付宝服务生成支付表单
        String paymentForm = aliPayService.createPaymentForm(
                order.getId().toString(),
                order.getTotalAmount(),
                "订单支付"
        );

        // 3. 构建返回结果
        ResultOrderPayVO result = new ResultOrderPayVO();
        result.setPaymentForm(paymentForm);
        result.setOrderId(order.getId().toString());
        result.setTotalAmount(String.valueOf(order.getTotalAmount()));
        result.setPaymentMethod(order.getPaymentMethod());
        return result;
    }

    @Override
    public void updateOrderStatus(String orderId, String alipayTradeNo, String amount) {
        // 1. 查找订单
        Orders order = orderRepository.findById(Integer.parseInt(orderId))
                .orElseThrow(TomatoMallException::OrderNotFound);

        // 2. 验证订单状态（防止重复处理）
        if (!"PENDING".equals(order.getStatus())) {
            throw TomatoMallException.OrderStatusError();
        }

        // 3. 更新订单信息
        order.setStatus("PAID");
        order.setAlipayTradeNo(alipayTradeNo);
        order.setPaidAmount(new BigDecimal(amount));
        order.setPayTime(LocalDateTime.now());

        // 4. 保存更新
        orderRepository.save(order);
    }
}
