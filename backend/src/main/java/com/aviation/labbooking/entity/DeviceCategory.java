package com.aviation.labbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备分类实体类
 * 映射数据库表device_category，用于存储设备分类信息
 */
@Data
@Entity
@Table(name = "device_category")
public class DeviceCategory {
    
    /**
     * 主键ID，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 分类名称，不能为空
     */
    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;
    
    /**
     * 分类描述
     */
    @Column(length = 200)
    private String description;
    
    /**
     * 排序号，默认0
     */
    @Column(name = "sort_order")
    private Integer sortOrder = 0;
    
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
}