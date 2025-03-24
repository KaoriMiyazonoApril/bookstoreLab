package com.example.tomatomall.repository;

import com.example.tomatomall.po.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByPhone(String phone);
    Account findByUsername(String username);
    Account findByUsernameAndPassword(String username,String password);
}
