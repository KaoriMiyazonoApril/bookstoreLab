package com.example.tomatomall.serviceImpl;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Account;
import com.example.tomatomall.repository.AccountRepository;
import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.util.SecurityUtil;
import com.example.tomatomall.util.TokenUtil;
import com.example.tomatomall.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TokenUtil tk;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    OssService ossService;


    public Boolean register(AccountVO accountVO, MultipartFile avatarFile) {
        Account account = accountRepository.findByPhone(accountVO.getPhone());
        if (account != null) {
            throw TomatoMallException.phoneAlreadyExists();
        }
        Account newUser = accountVO.toPO();
        try {
            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarUrl = ossService.upload(avatarFile, "avatars");
                newUser.setAvatar(avatarUrl);
            }
        } catch (IOException e) {
            throw new TomatoMallException("头像上传失败");
        }
        newUser.setPassword(passwordEncoder.encode(accountVO.getPassword()));
        accountRepository.save(newUser);
        return true;
    }

    @Override
    public AccountVO getUserByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw TomatoMallException.WrongUsername();
        }
        return account.toVO();
    }

    @Override
    public String login(String username, String pwd) {
        Account ac = accountRepository.findByUsername(username);
        if (ac == null) {
            throw TomatoMallException.WrongUsername();
        }

        // 使用matches方法验证密码
        if (!passwordEncoder.matches(pwd, ac.getPassword())) {
            throw TomatoMallException.WrongPassword();
        }

        return tk.getToken(ac);
    }

    @Override
    public Boolean update(String username, String password, String name, String avatar, String telephone, String email, String location) {
        Account ac = accountRepository.findByUsername(username);
        if (ac == null) {
            throw TomatoMallException.WrongUsername();
        }
        if (name != null) {
            ac.setName(name);
        }
        if (avatar != null) {
            ac.setAvatar(avatar);
        }
        if (telephone != null) {
            ac.setPhone(telephone);
        }
        if (email != null) {
            ac.setEmail(email);
        }
        if (location != null) {
            ac.setLocation(location);
        }

        if (password != null) {
            // 加密新密码
            ac.setPassword(passwordEncoder.encode(password));
        }

        accountRepository.save(ac);
        return true;
    }
}

