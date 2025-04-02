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
    public Response<Boolean> createUser(@RequestBody AccountVO accountVO) {
        return Response.buildSuccess(accountService.register(accountVO));
    }

    /**
     * 更新用户信息
     */
    @PutMapping()
    // 在更新方法中添加role参数
    public Response updateUser(@RequestParam(required = true,name="username") String username,
                             @RequestParam(required = false,name="password") String password,
                             @RequestParam(required = false,name="name") String name,
                             @RequestParam(required = false,name="avatar") String avatar,
                             @RequestParam(required = false,name="telephone") String telephone,
                             @RequestParam(required = false,name="email") String email,
                             @RequestParam(required = false,name="location") String location,
                             @RequestParam(required = false,name="role") String role) {

        return Response.buildSuccess(accountService.update(username,password,name,avatar,telephone,email,location));
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Response login(@RequestParam(required=true,name="username") String username,@RequestParam(required = true,name="password") String password) {
        return Response.buildSuccess(accountService.login(username,password));
    }
}
