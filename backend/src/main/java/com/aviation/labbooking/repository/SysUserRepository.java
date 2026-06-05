package com.aviation.labbooking.repository;

import com.aviation.labbooking.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户Repository接口
 * 继承JpaRepository获得基础的CRUD功能
 * 用于操作数据库的sys_user表
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象的Optional包装
     */
    Optional<SysUser> findByUsername(String username);
    
    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return true表示存在，false表示不存在
     */
    boolean existsByUsername(String username);
}