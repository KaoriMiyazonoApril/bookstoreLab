package com.example.tomatomall.repository;

import com.example.tomatomall.po.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsRepository extends JpaRepository<Carts, Integer> {
    Carts findByUserIdAndProductId(Integer userId, Integer productId);
}
