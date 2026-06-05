package com.aviation.labbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 预约记录实体类
 * 映射数据库表booking_record，用于存储设备预约记录
 */
@Data
@Entity
@Table(name = "booking_record", indexes = {
    @Index(name = "idx_booking_no", columnList = "booking_no"),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_device_id", columnList = "device_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_booking_start_time", columnList = "booking_start_time")
})
public class BookingRecord {
    
    /**
     * 主键ID，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 预约单号，唯一且不能为空
     */
    @Column(name = "booking_no", nullable = false, unique = true, length = 50)
    private String bookingNo;
    
    /**
     * 预约用户ID（外键关联sys_user表）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SysUser user;
    
    /**
     * 设备ID（外键关联device表）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    
    /**
     * 预约开始时间，不能为空
     */
    @Column(name = "booking_start_time", nullable = false)
    private LocalDateTime bookingStartTime;
    
    /**
     * 预约结束时间，不能为空
     */
    @Column(name = "booking_end_time", nullable = false)
    private LocalDateTime bookingEndTime;
    
    /**
     * 实际使用开始时间
     */
    @Column(name = "actual_start_time")
    private LocalDateTime actualStartTime;
    
    /**
     * 实际使用结束时间
     */
    @Column(name = "actual_end_time")
    private LocalDateTime actualEndTime;
    
    /**
     * 使用目的，不能为空
     */
    @Column(nullable = false, length = 200)
    private String purpose;
    
    /**
     * 项目名称
     */
    @Column(name = "project_name", length = 100)
    private String projectName;
    
    /**
     * 预约状态：PENDING-待审批，APPROVED-已批准，REJECTED-已拒绝，CANCELLED-已取消，IN_PROGRESS-进行中，COMPLETED-已完成
     */
    @Column(length = 20)
    private String status = "PENDING";
    
    /**
     * 审批人ID（外键关联sys_user表）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private SysUser approver;
    
    /**
     * 审批意见
     */
    @Column(name = "approval_comment", length = 200)
    private String approvalComment;
    
    /**
     * 拒绝原因
     */
    @Column(name = "rejection_reason", length = 200)
    private String rejectionReason;
    
    /**
     * 备注
     */
    @Column(length = 200)
    private String remark;
    
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