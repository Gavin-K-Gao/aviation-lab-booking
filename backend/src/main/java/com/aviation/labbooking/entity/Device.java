package com.aviation.labbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 设备实体类
 * 映射数据库表device，用于存储设备信息
 */
@Data
@Entity
@Table(name = "device", indexes = {
    @Index(name = "idx_device_code", columnList = "device_code"),
    @Index(name = "idx_category_id", columnList = "category_id"),
    @Index(name = "idx_status", columnList = "status")
})
public class Device {
    
    /**
     * 主键ID，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 设备编号，唯一且不能为空
     */
    @Column(name = "device_code", nullable = false, unique = true, length = 50)
    private String deviceCode;
    
    /**
     * 设备名称，不能为空
     */
    @Column(name = "device_name", nullable = false, length = 100)
    private String deviceName;
    
    /**
     * 品牌
     */
    @Column(length = 50)
    private String brand;
    
    /**
     * 型号
     */
    @Column(length = 50)
    private String model;
    
    /**
     * 规格参数
     */
    @Column(columnDefinition = "TEXT")
    private String specification;
    
    /**
     * 分类ID（外键关联device_category表）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private DeviceCategory category;
    
    /**
     * 所在位置
     */
    @Column(length = 100)
    private String location;
    
    /**
     * 采购日期
     */
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    
    /**
     * 保修期（月）
     */
    @Column(name = "warranty_period")
    private Integer warrantyPeriod;
    
    /**
     * 采购价格
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    /**
     * 设备状态：AVAILABLE-可用，IN_USE-使用中，MAINTENANCE-维护中，BROKEN-损坏，RETIRED-退役
     */
    @Column(length = 20)
    private String status = "AVAILABLE";
    
    /**
     * 设备描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * 设备图片URL
     */
    @Column(name = "image_url", length = 255)
    private String imageUrl;
    
    /**
     * 二维码内容
     */
    @Column(name = "qr_code", length = 255)
    private String qrCode;
    
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