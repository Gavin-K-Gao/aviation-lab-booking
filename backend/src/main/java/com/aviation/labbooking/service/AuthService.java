package com.aviation.labbooking.service;

import com.aviation.labbooking.dto.TokenResponse;

/**
 * 认证服务接口
 */
public interface AuthService {
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return Token响应
     */
    TokenResponse login(String username, String password);
}
