package com.example.tomatomall.service;

import com.example.tomatomall.vo.AccountVO;



public interface AccountService {

    Boolean register(AccountVO accountVO);
    AccountVO getUserByUsername(String username);
    //AccountVO getInformation();

    String login(String username,String password);
    Boolean update(String username,String password,String name,String avatar,String telephone,String email,String location);
}
