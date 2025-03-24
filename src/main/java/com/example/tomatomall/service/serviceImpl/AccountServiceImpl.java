package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Account;
import com.example.tomatomall.repository.AccountRepository;
import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.util.TokenUtil;
import com.example.tomatomall.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TokenUtil tk;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public String login(String username,String pwd){
        Account ac=accountRepository.findByUsername(username);
        if(ac==null){
            throw TomatoMallException.WrongUsername();
        }

        if(!passwordEncoder.matches(pwd,ac.getPassword())){
            throw TomatoMallException.WrongPassword();
        }

        return tk.getToken(ac);
    }

    @Override
    public Boolean update(String username,String password,String name,String avatar,String telephone,String email,String location){
        Account ac=accountRepository.findByUsername(username);
        if(ac==null){
            throw TomatoMallException.WrongUsername();
        }
        if(name!=null){
            ac.setName(name);
        }
        if(avatar!=null){
            ac.setAvatar(avatar);
        }
        if(telephone!=null){
            ac.setTelephone(telephone);
        }
        if(email!=null){
            ac.setEmail(email);
        }
        if(location!=null){
            ac.setLocation(location);
        }

        if(password!=null){
            ac.setPassword(passwordEncoder.encode(password));
        }

        accountRepository.save(ac);
        return true;
    }
}
