package com.example.tomatomall.service;


import com.example.tomatomall.vo.AccountVO;

public interface AccountService {

    Boolean register(AccountVO accountVO);
    AccountVO getInformation();
}
