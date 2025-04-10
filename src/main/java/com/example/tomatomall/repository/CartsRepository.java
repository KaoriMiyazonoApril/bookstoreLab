package com.example.tomatomall.repository;

import com.example.tomatomall.po.Carts;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.po.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartsRepository extends JpaRepository<Carts, Integer> {
    // 根据用户和商品查找购物车项
    Carts findByAccountAndProduct(Account account, Product product);
    // 根据用户查找所有购物车项
    List<Carts> findByAccount(Account account);
    Carts findByCartItemId(Integer cartItemId);
}
