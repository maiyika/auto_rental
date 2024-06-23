package com.coder.rental.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coder.rental.entity.User;
import com.coder.rental.service.IUserService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/rental/user")
public class UserController {
	@Resource
	public IUserService userService;

	@GetMapping
	public Result<List<User>> list() {
		return Result.success(userService.list());   //显示所有用户
	}
}
