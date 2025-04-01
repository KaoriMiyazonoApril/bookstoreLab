package com.example.tomatomall.service;

import com.example.tomatomall.vo.AccountVO;
import org.springframework.web.multipart.MultipartFile;


public interface AccountService {

    Boolean register(AccountVO accountVO, MultipartFile avatarFile);
    AccountVO getUserByUsername(String username);

    String login(String username,String password);
    Boolean update(String username,String password,String name,String avatar,String telephone,String email,String location);
}
