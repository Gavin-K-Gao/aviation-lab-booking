package com.aviation.labbooking.service.impl;

import com.aviation.labbooking.entity.SysUser;
import com.aviation.labbooking.repository.SysUserRepository;
import com.aviation.labbooking.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户Service实现类
 * 实现用户相关的业务逻辑
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {
    
    /**
     * 用户Repository，用于数据库操作
     */
    private final SysUserRepository sysUserRepository;
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象的Optional包装
     */
    @Override
    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById(id);
    }
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象的Optional包装
     */
    @Override
    public Optional<SysUser> findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    @Override
    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }
    
    /**
     * 保存或更新用户
     * @param sysUser 用户对象
     * @return 保存后的用户对象
     */
    @Override
    public SysUser save(SysUser sysUser) {
        return sysUserRepository.save(sysUser);
    }
    
    /**
     * 根据ID删除用户
     * @param id 用户ID
     */
    @Override
    public void deleteById(Long id) {
        sysUserRepository.deleteById(id);
    }
    
    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return true表示存在，false表示不存在
     */
    @Override
    public boolean existsByUsername(String username) {
        return sysUserRepository.existsByUsername(username);
    }
}