CREATE DATABASE IF NOT EXISTS `aviation_lab_booking` 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci; 

USE `aviation_lab_booking`; 

CREATE TABLE `sys_user` ( 
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID', 
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名', 
  `password` VARCHAR(100) NOT NULL COMMENT '密码（加密存储）', 
  `real_name` VARCHAR(50) COMMENT '真实姓名', 
  `email` VARCHAR(100) COMMENT '邮箱', 
  `phone` VARCHAR(20) COMMENT '手机号', 
  `avatar` VARCHAR(255) COMMENT '头像URL', 
  `role` VARCHAR(20) DEFAULT 'STUDENT' COMMENT '角色：ADMIN/TEACHER/STUDENT', 
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-禁用', 
  `last_login_time` DATETIME COMMENT '最后登录时间', 
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', 
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', 
  INDEX `idx_username` (`username`), 
  INDEX `idx_role` (`role`), 
  INDEX `idx_status` (`status`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表'; 

CREATE TABLE `device_category` ( 
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID', 
  `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称', 
  `description` VARCHAR(200) COMMENT '分类描述', 
  `sort_order` INT DEFAULT 0 COMMENT '排序号', 
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', 
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备分类表'; 

CREATE TABLE `device` ( 
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID', 
  `device_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '设备编号', 
  `device_name` VARCHAR(100) NOT NULL COMMENT '设备名称', 
  `brand` VARCHAR(50) COMMENT '品牌', 
  `model` VARCHAR(50) COMMENT '型号', 
  `specification` TEXT COMMENT '规格参数', 
  `category_id` BIGINT COMMENT '分类ID', 
  `location` VARCHAR(100) COMMENT '所在位置', 
  `purchase_date` DATE COMMENT '采购日期', 
  `warranty_period` INT COMMENT '保修期（月）', 
  `price` DECIMAL(10,2) COMMENT '采购价格', 
  `status` VARCHAR(20) DEFAULT 'AVAILABLE' COMMENT '状态：AVAILABLE-可用，IN_USE-使用中，MAINTENANCE-维护中，BROKEN-损坏，RETIRED-退役', 
  `description` TEXT COMMENT '设备描述', 
  `image_url` VARCHAR(255) COMMENT '设备图片URL', 
  `qr_code` VARCHAR(255) COMMENT '二维码内容', 
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', 
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', 
  INDEX `idx_device_code` (`device_code`), 
  INDEX `idx_category_id` (`category_id`), 
  INDEX `idx_status` (`status`), 
  FOREIGN KEY (`category_id`) REFERENCES `device_category`(`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备表'; 

CREATE TABLE `booking_record` ( 
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID', 
  `booking_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '预约单号', 
  `user_id` BIGINT NOT NULL COMMENT '预约用户ID', 
  `device_id` BIGINT NOT NULL COMMENT '设备ID', 
  `booking_start_time` DATETIME NOT NULL COMMENT '预约开始时间', 
  `booking_end_time` DATETIME NOT NULL COMMENT '预约结束时间', 
  `actual_start_time` DATETIME COMMENT '实际使用开始时间', 
  `actual_end_time` DATETIME COMMENT '实际使用结束时间', 
  `purpose` VARCHAR(200) NOT NULL COMMENT '使用目的', 
  `project_name` VARCHAR(100) COMMENT '项目名称', 
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态：PENDING-待审批，APPROVED-已批准，REJECTED-已拒绝，CANCELLED-已取消，IN_PROGRESS-进行中，COMPLETED-已完成', 
  `approver_id` BIGINT COMMENT '审批人ID', 
  `approval_comment` VARCHAR(200) COMMENT '审批意见', 
  `rejection_reason` VARCHAR(200) COMMENT '拒绝原因', 
  `remark` VARCHAR(200) COMMENT '备注', 
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', 
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', 
  INDEX `idx_booking_no` (`booking_no`), 
  INDEX `idx_user_id` (`user_id`), 
  INDEX `idx_device_id` (`device_id`), 
  INDEX `idx_status` (`status`), 
  INDEX `idx_booking_start_time` (`booking_start_time`), 
  FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`), 
  FOREIGN KEY (`device_id`) REFERENCES `device`(`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约记录表'; 

CREATE TABLE `maintenance_record` ( 
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID', 
  `device_id` BIGINT NOT NULL COMMENT '设备ID', 
  `maintenance_type` VARCHAR(20) NOT NULL COMMENT '维护类型：REPAIR-维修，INSPECTION-检查，CALIBRATION-校准，CLEANING-清洁', 
  `description` TEXT NOT NULL COMMENT '维护描述', 
  `maintainer` VARCHAR(50) NOT NULL COMMENT '维护人员', 
  `maintenance_cost` DECIMAL(8,2) COMMENT '维护费用', 
  `start_time` DATETIME NOT NULL COMMENT '维护开始时间', 
  `end_time` DATETIME COMMENT '维护结束时间', 
  `status` VARCHAR(20) DEFAULT 'IN_PROGRESS' COMMENT '状态：IN_PROGRESS-进行中，COMPLETED-已完成', 
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', 
  INDEX `idx_device_id` (`device_id`), 
  INDEX `idx_maintenance_type` (`maintenance_type`), 
  INDEX `idx_status` (`status`), 
  FOREIGN KEY (`device_id`) REFERENCES `device`(`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维护记录表'; 

INSERT INTO `sys_user` (`username`, `password`, `real_name`, `email`, `role`) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', 'admin@aviation-lab.com', 'ADMIN'), 
('teacher01', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张老师', 'teacher01@aviation-lab.com', 'TEACHER'), 
('student01', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李同学', 'student01@aviation-lab.com', 'STUDENT'); 

INSERT INTO `device_category` (`category_name`, `description`, `sort_order`) VALUES 
('电子测量仪器', '示波器、信号发生器、频谱分析仪等', 1), 
('机械加工设备', '车床、铣床、钻床等', 2), 
('光学实验设备', '激光干涉仪、光谱仪、显微镜等', 3), 
('计算机与网络设备', '工作站、服务器、交换机等', 4), 
('化学分析设备', '色谱仪、质谱仪、分光光度计等', 5);