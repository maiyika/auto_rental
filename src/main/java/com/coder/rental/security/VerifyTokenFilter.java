package com.coder.rental.security;

import cn.hutool.core.util.StrUtil;
import com.coder.rental.utils.JwtUtils;
import com.coder.rental.utils.RedisUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Barry
 * @project auto_rental
 * @date 19/6/2024
 */

/**
 * 验证token过滤器
 * OncePerRequestFilter--Springboot
 * 应用于Spring Security 过滤请求
 * 每次请求时都会执行
 */
@Component
public class VerifyTokenFilter extends OncePerRequestFilter {
	@Value("${request.login-url}")
	private String loginUrl;
	@Resource
	private RedisUtils redisUtil;
	@Resource
	CustomerUserDetailsService customerUserDetailsService;
	@Resource
	private LoginFailureHandler loginFailureHandler;

	@Override
	protected void doFilterInternal( HttpServletRequest request,
	                                 HttpServletResponse response,
	                                 FilterChain filterChain ) throws ServletException, IOException {
		//获取请求url
		String url = request.getRequestURI();
		if ( !StrUtil.equals(url, loginUrl) ) {  //如果不是登录请求, 则验证token
			try {
				validateToken(request, response);
			} catch ( AuthenticationException e ) {
				loginFailureHandler.onAuthenticationFailure(request, response, e);
			}
		}
		doFilter(request, response, filterChain);
	}

	private void validateToken( HttpServletRequest request,
	                            HttpServletResponse response ) throws AuthenticationException {
		//校验token是否存在
		String token = request.getHeader("token");
		if ( StrUtil.isEmpty(token) ) {
			token = request.getParameter("token");
		}
		if ( StrUtil.isEmpty(token) ) {
			throw new CustomerAuthenticationException("token为空");
		}

		//校验token是否合法(与redis中的token比对)
		String tokenKey = "token:" + token;
		String tokenValue = redisUtil.get(tokenKey);
		if ( StrUtil.isEmpty(tokenValue) ) {
			throw new CustomerAuthenticationException("token无效");
		}
		if ( !StrUtil.equals(token, tokenValue) ) {
			throw new CustomerAuthenticationException("token验证失败");
		}
		String username = JwtUtils.parseToken(token).getClaim("username").toString();
		UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
		if ( StrUtil.isEmpty(username) || userDetails == null ) {
			throw new CustomerAuthenticationException("用户不存在");
		}

		//创建并设置认证信息
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource()
				.buildDetails(request));
		//设置认证信息到上下文
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
