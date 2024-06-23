package com.coder.rental.security;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Barry
 * @project auto_rental
 * @date 18/6/2024
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Resource
	private LoginSuccessHandler loginSuccessHandler;
	@Resource
	private LoginFailureHandler loginFailureHandler;
	@Resource
	private CustomerAccessDeniedHandler customerAccessDeniedHandler;
	@Resource
	private CustomerAnonymousEntryPoint customerAnonymousEntryPoint;
	@Resource
	private CustomerUserDetailsService customerUserDetailsService;
	@Resource
	VerifyTokenFilter verifyTokenFilter;

	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
		http.addFilterBefore(verifyTokenFilter, UsernamePasswordAuthenticationFilter.class); // 添加验证token过滤器
		//登录过程处理
		http.formLogin()  // 开启表单登录
				.loginProcessingUrl("/rental/user/login")  // 设置登录处理的URL
				.successHandler(loginSuccessHandler)  // 登录成功时的处理器
				.failureHandler(loginFailureHandler)  // 登录失败时的处理器
				.and()
				.sessionManagement()  // 开启会话管理
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 设置会话创建策略为无状态，即不创建会话
				.and()
				.authorizeHttpRequests()  // 开启请求授权
				.requestMatchers("/rental/user/login").permitAll()  // 对登录URL的请求允许所有人访问
				.anyRequest().authenticated()  // 对其他任何请求都需要认证
				.and()
				.exceptionHandling()  // 开启异常处理
				.authenticationEntryPoint(customerAnonymousEntryPoint)  // 设置匿名用户访问无权限资源时的异常处理
				.accessDeniedHandler(customerAccessDeniedHandler)  // 设置访问被拒绝时的异常处理
				.and()
				.cors()  // 开启跨域资源共享
				.and()
				.csrf().disable()  // 禁用跨站请求伪造防护
				.userDetailsService(customerUserDetailsService);  // 设置用户详细信息服务
		return http.build();  // 构建并返回SecurityFilterChain
	}
}
