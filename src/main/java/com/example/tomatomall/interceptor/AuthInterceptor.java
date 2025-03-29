package com.example.tomatomall.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MimeHeaders;

import com.example.tomatomall.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenUtil tk;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 放行GET请求（由Spring Security管理权限）
        if ("GET".equalsIgnoreCase(method)) {
            return true;
        }

        // 保留原有的POST请求放行逻辑
        if ("/api/accounts".equals(uri) && "POST".equalsIgnoreCase(method)) {
            return true;
        }

        // 其他鉴权逻辑
        String token = request.getHeader("Authorization");
        if (!tk.verifyToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":\"400\",\"msg\":\"未授权，请先登录\",\"data\":null}");
            return false;
        }

        return true;
    }
}
