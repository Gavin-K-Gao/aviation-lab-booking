package com.aviation.labbooking.controller;

import com.aviation.labbooking.common.Result;
import com.aviation.labbooking.dto.LoginRequest;
import com.aviation.labbooking.dto.TokenResponse;
import com.aviation.labbooking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证管理", description = "用户认证相关接口")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     * @param request 登录请求
     * @return Token响应
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录获取Token")
    public Result<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse token = authService.login(request.getUsername(), request.getPassword());
        return Result.success(token);
    }
}
