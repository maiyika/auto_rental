package com.coder.rental.utils;

/**
 * @author Barry
 * @project auto_rental
 * @date 16/6/2024
 */
public class ResultCode {

	/**
	 * 定义HttpStatus状态码
	 */
	public static final Integer SUCCESS = 200; //成功
	public static final Integer ERROR = 500;  //失败
	public static final Integer UNAUTHENTICATED = 401;  //未认证
	public static final Integer UNAUTHORIZED= 403;  //未授权
	public static final Integer NOT_FOUND = 404;  //未找到
}
