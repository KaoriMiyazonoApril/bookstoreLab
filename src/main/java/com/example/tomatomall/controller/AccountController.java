package com.example.tomatomall.controller;

import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.vo.AccountVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    /**
     * 获取用户详情
     */
    @GetMapping("/{username}")
    public Response<AccountVO> getUser(@PathVariable String username) {
        return Response.buildSuccess(accountService.getUserByUsername(username));
    }

    /**
     * 创建新的用户
     */
    // 在注册方法中添加role参数
    @PostMapping()
    public Response createUser(@RequestBody AccountVO accountVO) {
        return Response.buildSuccess(accountService.register(accountVO));
    }

    /**
     * 更新用户信息
     */
    @PutMapping()
    // 在更新方法中添加role参数
    public Response updateUser(@RequestBody AccountVO a) {
        return Response.buildSuccess(accountService.update(a.getUsername(), a.getPassword(), a.getName(), a.getAvatar(), a.getPhone(), a.getEmail(), a.getLocation()));
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Response login(@RequestBody AccountVO a) {
        return Response.buildSuccess(accountService.login(a.getUsername(), a.getPassword()));
    }
}
