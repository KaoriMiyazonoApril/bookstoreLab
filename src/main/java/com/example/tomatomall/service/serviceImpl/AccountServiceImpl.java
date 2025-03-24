package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.po.Account;
import com.example.tomatomall.repository.AccountRepository;
import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Boolean register(AccountVO accountVO) {
        Account account=accountRepository.findByPhone(accountVO.getPhone());
        if(account!=null){
            return false;
        }
        Account newAccount=accountVO.toPO();
        newAccount.setCreateTime(new Date());
        accountRepository.save(newAccount);
        return true;
    }

    @Override
    public AccountVO getInformation() {
        return null;
    }

}
