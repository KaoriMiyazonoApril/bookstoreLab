package com.example.tomatomall.repository;

import com.example.tomatomall.po.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad,Integer> {
    Ad findByIdAndProductId(Integer id,Integer productId);
}
