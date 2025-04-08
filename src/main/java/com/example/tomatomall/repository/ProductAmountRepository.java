package com.example.tomatomall.repository;

import com.example.tomatomall.po.ProductAmount;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductAmountRepository extends JpaRepository<ProductAmount,Integer>{
    ProductAmount findByProductId(Integer productId);
}
