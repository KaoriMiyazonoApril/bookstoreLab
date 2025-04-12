package com.example.tomatomall.repository;

import com.example.tomatomall.po.Carts;
import com.example.tomatomall.po.OrderDetail;
import com.example.tomatomall.po.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrder(Orders order);
    List<OrderDetail> findByCarts(Carts cartItem);
}
