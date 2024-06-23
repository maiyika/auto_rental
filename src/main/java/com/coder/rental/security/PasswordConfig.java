package com.coder.rental.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * @author Barry
 * @project auto_rental
 * @date 18/6/2024
 */
@Configuration
@Data
public class PasswordConfig {
	@Value("${encoder.ctype.strength}")
	private int strength;
	@Value("${encoder.ctype.secret}")
	private String secret;
	/**
	 * @return BCryptPasswordEncoder  强散列加密
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		SecureRandom secureRandom = new SecureRandom(secret.getBytes());
		return new BCryptPasswordEncoder(strength, secureRandom);
	}

}
