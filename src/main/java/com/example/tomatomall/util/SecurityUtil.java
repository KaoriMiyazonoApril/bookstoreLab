/*package com.example.tomatomall.util;

import com.example.tomatomall.po.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.servlet.http.HttpServletRequest;

@Configuration
public class SecurityUtil {

    @Autowired
    HttpServletRequest httpServletRequest;

    public Account getCurrentUser(){
        return (Account)httpServletRequest.getSession().getAttribute("currentAccount" );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/
package com.example.tomatomall.util;

import com.example.tomatomall.po.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: DingXiaoyu
 * @Date: 0:28 2023/11/26
 * 你可以通过这个类的方法来获得当前用户的信息。
 */
@Component
public class SecurityUtil {

    @Autowired
    HttpServletRequest httpServletRequest;

    public Account getCurrentUser(){
        return (Account)httpServletRequest.getSession().getAttribute("currentUser");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

