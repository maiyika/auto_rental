package com.coder.rental.security;

import com.alibaba.fastjson.JSON;
import com.coder.rental.utils.Result;
import com.coder.rental.utils.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Barry
 * @project auto_rental
 * @date 18/6/2024
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle( HttpServletRequest request,
	                    HttpServletResponse response,
	                    AccessDeniedException accessDeniedException ) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8"); //设置客户端响应类型
		ServletOutputStream outputStream = response.getOutputStream();
		String result= JSON.toJSONString(Result.fail().setMessage("权限不足").setCode(ResultCode.UNAUTHORIZED));
		outputStream.write(result.getBytes(StandardCharsets.UTF_8));
		outputStream.flush();
		outputStream.close();
	}
}
