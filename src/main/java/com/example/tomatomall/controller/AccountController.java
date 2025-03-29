package com.example.tomatomall.controller;

import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.util.TokenUtil;
import com.example.tomatomall.vo.AccountVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    TokenUtil tk;  // 添加这行

    /**
     * 获取用户详情
     */
    @GetMapping("/{username}")
    public Response getUser(@PathVariable String username) { // 移除@RequestHeader参数
        AccountVO accountVO = accountService.getInformation(username);
        return accountVO != null ?
            Response.buildSuccess(accountVO) :
            Response.buildFailure("用户不存在", "404");
    }

    /**
     * 创建新的用户
     */
    // 在注册方法中添加role参数
    @PostMapping()
    public Response createUser(@RequestBody AccountVO accountVO) {
        boolean result = accountService.register(accountVO);
        if (!result) {
            return Response.buildFailure("用户名已存在", "400");
        }
        return Response.buildSuccess("注册成功");
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
