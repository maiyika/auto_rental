package com.coder.rental.security;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWTPayload;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.coder.rental.entity.User;
import com.coder.rental.utils.JwtUtils;
import com.coder.rental.utils.RedisUtils;
import com.coder.rental.utils.ResultCode;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Barry
 * @project auto_rental
 * @date 17/6/2024
 */

/**
 * 登录成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Resource
	private RedisUtils redisUtils;

	@Override
	public void onAuthenticationSuccess( HttpServletRequest request,
	                                     HttpServletResponse response,
	                                     Authentication authentication ) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8"); //设置客户端响应类型
		User user = (User) authentication.getPrincipal(); //获取用户信息

		//TODO:生成token
		Map<String, Object> map = new HashMap<>() {
			{
				put("username", user.getUsername());
				put("userid", user.getId());
			}
		};
		String token = JwtUtils.generateToken(map); //生成token
		NumberWithFormat chaim = (NumberWithFormat) JwtUtils.parseToken(token).getClaim(JWTPayload.EXPIRES_AT);//获取token的过期时间
		long expireTime = Convert.toDate(chaim).getTime();//将过期时间转换为日期
		AuthenticationResult authenticationResult = new AuthenticationResult(user.getId(), ResultCode.SUCCESS, token, expireTime); //创建认证结果对象

		String result = JSON.toJSONString(authenticationResult,
				SerializerFeature.DisableCircularReferenceDetect); //将用户信息转换为json字符串,并去除循环引用

		ServletOutputStream outputStream = response.getOutputStream();  //获取输出流
		outputStream.write(result.getBytes(StandardCharsets.UTF_8));  //将json字符串写入输出流
		outputStream.flush();  //刷新输出流
		outputStream.close();  //关闭输出流

		String tokenKey = "token:" + token;  //生成token的key
		long nowTime = DateTime.now().getTime();  //获取当前时间
		redisUtils.set(tokenKey, token, (expireTime - nowTime) / 1000);  //将token存入redis,并设置过期时间
	}
}
