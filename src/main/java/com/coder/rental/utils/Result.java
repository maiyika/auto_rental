package com.coder.rental.utils;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Barry
 * @project auto_rental
 * @date 16/6/2024
 */
@Data
@Accessors(chain = true)
public class Result<T> {
	private Integer code;
	private String message;
	private T data;
	private Boolean success;

	private Result() {
	}

	public static <T> Result<T> success() {
		return new Result<T>().setCode(ResultCode.SUCCESS).setSuccess(true).setMessage("操作成功");
	}

	public static <T> Result<T> success(T data) {
		return new Result<T>().setCode(ResultCode.SUCCESS).setSuccess(true).setMessage("操作成功").setData(data);
	}
	public static <T> Result<T> fail() {
		return new Result<T>().setCode(ResultCode.ERROR).setSuccess(false).setMessage("操作失败");
	}

}
