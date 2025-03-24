package com.example.tomatomall.service;


import com.example.tomatomall.po.Account;
import com.example.tomatomall.repository.AccountRepository;
import com.example.tomatomall.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;

public interface AccountService {

    Boolean register(AccountVO accountVO);
    AccountVO getInformation();

    String login(String username,String password);
    Boolean update(String username,String password,String name,String avatar,String telephone,String email,String location);
}
