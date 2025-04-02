package com.example.tomatomall.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUser_Success() throws Exception {
        // 先获取有效token（需要先注册/登录测试用户）
        String token = "valid_token_here";

        mockMvc.perform(get("/api/accounts/existingUser")
                        .header("token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("existingUser"));
    }

    @Test
    void testGetUser_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/accounts/existingUser"))
                .andExpect(status().isForbidden());
    }
}