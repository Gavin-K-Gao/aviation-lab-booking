package com.aviation.labbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    /**
     * JWT Token
     */
    private String token;
    
    /**
     * Token类型
     */
    private String tokenType = "Bearer";
}
