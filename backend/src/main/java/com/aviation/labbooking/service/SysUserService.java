package com.aviation.labbooking.service;

import com.aviation.labbooking.entity.SysUser;

import java.util.List;
import java.util.Optional;

/**
 * 用户Service接口
 * 定义用户相关的业务逻辑
 */
public interface SysUserService {
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象的Optional包装
     */
    Optional<SysUser> findById(Long id);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象的Optional包装
     */
    Optional<SysUser> findByUsername(String username);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<SysUser> findAll();
    
    /**
     * 保存或更新用户
     * @param sysUser 用户对象
     * @return 保存后的用户对象
     */
    SysUser save(SysUser sysUser);
    
    /**
     * 根据ID删除用户
     * @param id 用户ID
     */
    void deleteById(Long id);
    
    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return true表示存在，false表示不存在
     */
    boolean existsByUsername(String username);
}