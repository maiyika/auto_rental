package com.coder.rental.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Barry
 * @project auto_rental
 * @date 19/6/2024
 */

/**
 * 认证结果
 */
@Data
@AllArgsConstructor
public class AuthenticationResult {
	private int id;
	private int code;
	private String token;
	private Long expireTime;
}
