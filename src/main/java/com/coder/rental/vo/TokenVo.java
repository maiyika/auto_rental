package com.coder.rental.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Barry
 * @project auto_rental
 * @date 19/6/2024
 */
@Data
@Accessors(chain = true)
public class TokenVo {
	private String token;
	private long expireTime;

}
