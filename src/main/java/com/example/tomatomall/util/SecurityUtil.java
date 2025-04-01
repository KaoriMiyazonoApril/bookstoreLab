package com.example.tomatomall.util;

import com.example.tomatomall.po.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.servlet.http.HttpServletRequest;

@Configuration
public class SecurityUtil extends WebSecurityConfigurerAdapter {

    @Autowired
    HttpServletRequest httpServletRequest;

    public Account getCurrentUser(){
        return (Account)httpServletRequest.getSession().getAttribute("currentAccount" );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 禁用CSRF
                .authorizeRequests()
                .antMatchers("/api/accounts/login", "/api/accounts").permitAll() // 放行注册和登录接口
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable(); // 禁用默认的Basic认证
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
