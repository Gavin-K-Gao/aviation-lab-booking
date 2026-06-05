package com.aviation.labbooking.service.impl;

import com.aviation.labbooking.dto.TokenResponse;
import com.aviation.labbooking.entity.SysUser;
import com.aviation.labbooking.repository.SysUserRepository;
import com.aviation.labbooking.service.AuthService;
import com.aviation.labbooking.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现类
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse login(String username, String password) {
        SysUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        String token = jwtTokenUtil.generateToken(user);
        return new TokenResponse(token, "Bearer");
    }
}
