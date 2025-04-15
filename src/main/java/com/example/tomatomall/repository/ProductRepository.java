package com.example.tomatomall.repository;

import com.example.tomatomall.po.Product;
import com.example.tomatomall.vo.ProductVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByCategory(Integer category);

    List<Product> findByTitleContainingOrDescriptionContainingOrCoverContainingOrDetailContaining(String title, String description, String cover, String detail);
}