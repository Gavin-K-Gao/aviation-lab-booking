
# Aviation Laboratory Equipment Booking System

航空实验室设备调度平台

## 项目简介

这是一个用于航空实验室设备预约和调度管理的系统。

## 技术栈

- Spring Boot 3.2.5
- Java 17
- Maven
- Swagger (springdoc-openapi)

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+

### 运行项目

1. 克隆项目
2. 在 IDEA 中打开 backend 目录
3. 运行 `LabBookingApplication`
4. 访问 http://localhost:8080/swagger-ui.html 查看 API 文档

## 项目结构

```
lab-booking/
├── src/
│   ├── main/
│   │   ├── java/com/aviation/labbooking/
│   │   │   ├── LabBookingApplication.java
│   │   │   ├── config/
│   │   │   ├── common/
│   │   │   ├── controller/
│   │   │   ├── utils/
│   │   │   └── annotation/
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── logback-spring.xml
│   └── test/
└── pom.xml
```

## API 文档

启动项目后访问：http://localhost:8080/swagger-ui.html
