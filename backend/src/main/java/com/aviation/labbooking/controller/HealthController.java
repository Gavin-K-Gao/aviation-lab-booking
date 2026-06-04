package com.aviation.labbooking.controller;

import com.aviation.labbooking.common.Result;
import com.aviation.labbooking.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/health")
@Tag(name = "健康检查", description = "系统健康状态检查接口")
public class HealthController {

    @Operation(summary = "系统健康检查", description = "返回系统运行状态")
    @GetMapping
    public Result<Map<String, Object>> healthCheck() {
        log.info("Health check requested at {}", LocalDateTime.now());

        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("service", "Aviation Lab Booking System");
        healthInfo.put("version", "1.0.0");
        healthInfo.put("timestamp", DateUtils.formatDateTime(LocalDateTime.now()));
        healthInfo.put("environment", System.getProperty("spring.profiles.active", "dev"));

        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        Map<String, Object> memoryInfo = new HashMap<>();
        memoryInfo.put("total", totalMemory / 1024 / 1024 + "MB");
        memoryInfo.put("used", usedMemory / 1024 / 1024 + "MB");
        memoryInfo.put("free", freeMemory / 1024 / 1024 + "MB");
        healthInfo.put("memory", memoryInfo);

        return Result.success(healthInfo);
    }

    @Operation(summary = "详细健康检查", description = "返回详细系统信息")
    @GetMapping("/detail")
    public Result<Map<String, Object>> detailedHealthCheck() {
        Map<String, Object> detailInfo = new HashMap<>();

        detailInfo.put("jvmVersion", System.getProperty("java.version"));
        detailInfo.put("jvmVendor", System.getProperty("java.vendor"));
        detailInfo.put("osName", System.getProperty("os.name"));
        detailInfo.put("osArch", System.getProperty("os.arch"));
        detailInfo.put("processors", Runtime.getRuntime().availableProcessors());

        return Result.success(detailInfo);
    }
}
