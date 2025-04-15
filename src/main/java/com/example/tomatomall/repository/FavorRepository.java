package com.example.tomatomall.repository;

import com.example.tomatomall.po.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavorRepository extends JpaRepository<FavoriteProduct,Integer> {
    List<FavoriteProduct> findByUserId(Integer userId);
    FavoriteProduct findByUserIdAndProductId(Integer userId,Integer productId);
}
