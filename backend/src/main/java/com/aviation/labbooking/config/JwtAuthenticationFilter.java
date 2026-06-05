package com.aviation.labbooking.config;

import com.aviation.labbooking.entity.SysUser;
import com.aviation.labbooking.repository.SysUserRepository;
import com.aviation.labbooking.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT认证过滤器
 * 用于验证请求中的JWT Token
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final SysUserRepository userRepository;

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/auth/",
            "/api/health/",
            "/swagger-ui/",
            "/v3/api-docs/"
    );

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        // 放行OPTIONS请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        
        // 放行公开路径
        String requestUri = request.getRequestURI();
        for (String publicPath : PUBLIC_PATHS) {
            if (requestUri.startsWith(publicPath)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                   @NonNull HttpServletResponse response, 
                                   @NonNull FilterChain filterChain) 
            throws ServletException, IOException {
        
        try {
            // 从请求头中获取Token
            final String authHeader = request.getHeader("Authorization");
            
            // 如果没有Token或者不是Bearer类型，继续过滤链（后续会被Spring Security拦截）
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // 提取Token
            final String jwt = authHeader.substring(7);
            final String username = jwtTokenUtil.extractUsername(jwt);
            
            // 如果用户名不为空且当前没有认证信息
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 查询用户
                SysUser user = userRepository.findByUsername(username).orElse(null);
                
                // 验证Token
                if (user != null && jwtTokenUtil.validateToken(jwt, user)) {
                    // 创建认证对象
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // 设置认证信息到上下文
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            log.error("JWT认证失败: {}", e.getMessage());
        }
        
        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}
