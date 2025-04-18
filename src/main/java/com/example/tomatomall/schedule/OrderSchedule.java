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

//定时处理超时订单
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
        System.out.println("开始检查过期订单");
        LocalDateTime now = LocalDateTime.now();
        //设置超时时间
        List<Orders> expiredOrders = ordersRepository.findByStatusAndCreateTimeBefore(
                "PENDING", now.minusMinutes(30)
        );
        if (expiredOrders.isEmpty()) {
            System.out.println("没有过期订单");
            return;
        }

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