package com.example.tomatomall.schedule;

import com.example.tomatomall.po.OrderDetail;
import com.example.tomatomall.po.Orders;
import com.example.tomatomall.repository.OrderDetailRepository;
import com.example.tomatomall.repository.OrderRepository;
import com.example.tomatomall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderSchedule {

    @Autowired
    private OrderRepository ordersRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Scheduled(fixedRate = 30000) // 每半分钟检查一次
    public void releaseExpiredOrders() {
        LocalDateTime now = LocalDateTime.now();
        List<Orders> expiredOrders = ordersRepository.findByStatusAndCreateTimeBefore(
                "PENDING", String.valueOf(now.minusMinutes(30))
        );

        for (Orders order : expiredOrders) {
            // 释放库存
            List<OrderDetail> orderTailList = orderDetailRepository.findByOrder(order);
            for (OrderDetail orderTail: orderTailList) {
                productService.releaseStock(orderTail.getProduct().getId(), orderTail.getCarts().getQuantity());
            }

            // 更新订单状态
            order.setStatus("CANCELED");
            ordersRepository.save(order);
        }
    }
}