package com.coder.rental.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Barry
 * @project auto_rental
 * @date 17/6/2024
 */
@Configuration
public class CORSConfig implements WebMvcConfigurer {
	/**
	 * @param registry 跨域配置
	 */
	@Override
	public void addCorsMappings( CorsRegistry registry ) {
		//允许跨域的路径
		registry.addMapping("/**")
				//允许跨域的域名, 可以用*表示允许任何域名使用
				.allowedOriginPatterns("*")
				//允许跨域的方法
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				//允许跨域的请求头
				.allowedHeaders("*")
				//是否允许发送Cookie
				.allowCredentials(true)
				//预检请求的有效期, 单位为秒
				.maxAge(3600);
	}
}
