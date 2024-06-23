package com.coder.rental.security;

import com.alibaba.fastjson.JSON;
import com.coder.rental.utils.Result;
import com.coder.rental.utils.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * @author Barry
 * @project auto_rental
 * @date 17/6/2024
 */

/**
 * 登录失败处理器
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure( HttpServletRequest request,
	                                     HttpServletResponse response,
	                                     AuthenticationException exception ) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8"); //设置客户端响应类型

		ServletOutputStream outputStream = response.getOutputStream(); //获取输出流
		int code = ResultCode.ERROR;
		String msg = null;
		if ( exception instanceof AccountExpiredException ) {
			code = ResultCode.UNAUTHENTICATED;
			msg = "账号已过期";
		} else if ( exception instanceof BadCredentialsException ) {
			code = ResultCode.UNAUTHENTICATED;
			msg = "用户名或密码错误";
		} else if ( exception instanceof CredentialsExpiredException ) {
			code = ResultCode.UNAUTHENTICATED;
			msg = "密码过期";
		} else if(exception instanceof DisabledException ){
			code = ResultCode.UNAUTHORIZED;
			msg = "账号被禁用";
		} else if ( exception instanceof LockedException ) {
			code =ResultCode.UNAUTHORIZED;
			msg = "账号被锁定";
		}else if ( exception instanceof InternalAuthenticationServiceException ) {
			code =ResultCode.UNAUTHENTICATED;
			msg = "账号不存在";
		}else if ( exception instanceof CustomerAuthenticationException ) {
			code =ResultCode.UNAUTHORIZED;
			msg = exception.getMessage();
		}else {
			msg = "登录失败";
		}
		String result = JSON.toJSONString(Result.fail().setCode(code).setMessage(msg)); //将结果转换为json字符串
		outputStream.write(result.getBytes(StandardCharsets.UTF_8)); //将json字符串写入输出流
		outputStream.flush(); //刷新输出流
		outputStream.close(); //关闭输出流
	}
}
