package com.coder.rental.config;

import com.coder.rental.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Barry
 * @project auto_rental
 * @date 17/6/2024
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

	// 捕获所有异常(待优化)
	@ExceptionHandler(value = Exception.class)
	public Result handleException( Exception e ) {
		String eMessage = e.getMessage();
		log.error("异常信息: {}", eMessage, e);
		return Result.fail().setMessage(eMessage);
	}

}
