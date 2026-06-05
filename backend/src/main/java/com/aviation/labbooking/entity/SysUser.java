package com.aviation.labbooking.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * 系统用户实体类
 * 映射数据库表sys_user，用于存储用户信息
 * 实现UserDetails接口，为Spring Security提供认证信息
 */
@Data
@Entity
@Table(name = "sys_user")
public class SysUser implements UserDetails {
    
    /**
     * 主键ID，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户名，唯一且不能为空
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    /**
     * 密码（加密存储），不能为空
     */
    @Column(nullable = false, length = 100)
    private String password;
    
    /**
     * 真实姓名
     */
    @Column(length = 50)
    private String realName;
    
    /**
     * 邮箱
     */
    @Column(length = 100)
    private String email;
    
    /**
     * 手机号
     */
    @Column(length = 20)
    private String phone;
    
    /**
     * 头像URL
     */
    @Column(length = 255)
    private String avatar;
    
    /**
     * 用户角色：ADMIN-管理员，TEACHER-教师，STUDENT-学生
     */
    @Column(nullable = false, length = 20)
    private String role;
    
    /**
     * 用户状态：1-正常，0-禁用
     */
    @Column(nullable = false)
    private Integer status = 1;
    
    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;
    
    /**
     * 创建时间，不可更新
     */
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    
    /**
     * 保存前自动设置创建时间和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }
    
    /**
     * 更新前自动设置更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
    
    /**
     * Spring Security UserDetails 实现
     * 获取用户权限列表
     * @return 权限列表，包含角色前缀ROLE_
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
    
    /**
     * 账户是否未过期
     * @return true表示未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    /**
     * 账户是否未锁定
     * @return true表示未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return status == 1;
    }
    
    /**
     * 凭证是否未过期
     * @return true表示未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    /**
     * 账户是否启用
     * @return true表示启用
     */
    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}