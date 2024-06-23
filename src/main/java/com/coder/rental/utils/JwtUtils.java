package com.coder.rental.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Barry
 * @project auto_rental
 * @date 17/6/2024
 */
@Component
public class JwtUtils {
	public static final String SECRET_KEY = "Barry";
	public static final long EXPIRE_TIME = 1000L * 60 * 30;

	/**
	 * 生成token
	 *
	 * @param payload token携带的信息, 例如用户ID; 将被添加签发时间, 过期时间, 生效时间
	 * @return token
	 */
	public static String generateToken( Map<String, Object> payload ) {
		DateTime now = DateTime.now();
		DateTime expireTime = new DateTime(now.getTime() + EXPIRE_TIME);
		//设置签发时间
		payload.put(JWTPayload.ISSUED_AT, now);
		//设置过期时间
		payload.put(JWTPayload.EXPIRES_AT, expireTime);
		//设置生效时间, 立即生效
		payload.put(JWTPayload.NOT_BEFORE, now);
		return JWTUtil.createToken(payload, SECRET_KEY.getBytes());
	}


	/**
	 * 解析token
	 *
	 * @param token token
	 * @return JWTPayload token携带的信息
	 */
	public static JWTPayload parseToken( String token ) {
		JWT jwt = JWTUtil.parseToken(token);
		if ( !jwt.setKey(SECRET_KEY.getBytes()).verify() ) {
			throw new RuntimeException("Token验证失败");
		}
		if ( !jwt.validate(0) ) {
			throw new RuntimeException("Token已过期");
		}
		return jwt.getPayload();
	}

}
