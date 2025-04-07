package com.example.tomatomall.repository;

import com.example.tomatomall.po.Carts;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.po.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartsRepository extends JpaRepository<Carts, Integer> {
    Carts findByAccountAndProduct(Account account, Product product);
    List<Carts> findByAccount(Account account);
}
