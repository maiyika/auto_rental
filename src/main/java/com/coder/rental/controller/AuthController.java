package com.coder.rental.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import com.coder.rental.entity.Permission;
import com.coder.rental.entity.User;
import com.coder.rental.security.AuthenticationResult;
import com.coder.rental.security.CustomerAuthenticationException;
import com.coder.rental.service.IRoleService;
import com.coder.rental.service.impl.RoleServiceImpl;
import com.coder.rental.utils.*;
import com.coder.rental.vo.RouteVo;
import com.coder.rental.vo.TokenVo;
import com.coder.rental.vo.UserInfoVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Barry
 * @project auto_rental
 * @date 19/6/2024
 */

@RestController
@RequestMapping("/rental/auth")
public class AuthController {

	@Resource
	private RedisUtils redisUtils;
	@Resource
	private RouteTreeUtils RouteTreeUtils;
	@Resource
	private IRoleService roleService;

	@PostMapping("/refreshToken")
	public Result refreshToken( HttpServletRequest request ) {  //刷新token
		String token = request.getHeader("token");
		if ( StrUtil.isEmpty(token) ) {
			token = request.getParameter("token");
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails UserDetails = (UserDetails) authentication.getPrincipal();
		String username = JwtUtils.parseToken(token).getClaim("username").toString();
		String newToken = null;
		if ( StrUtil.equals(username, UserDetails.getUsername()) ) {
			Map<String, Object> map = new HashMap<>();
			map.put("username", UserDetails.getUsername());
			newToken = JwtUtils.generateToken(map); //生成新token
		} else {
			throw new CustomerAuthenticationException("token数据异常");
		}
		//获取本次token的过期时间
		NumberWithFormat chaim = (NumberWithFormat) JwtUtils.parseToken(newToken).getClaim(JWTPayload.EXPIRES_AT);
		long expireTime = Convert.toDate(chaim).getTime();
		TokenVo tokenVo = new TokenVo();   //创建token包装对象
		tokenVo.setToken(newToken).setExpireTime(expireTime);
		//清除原有的token
		redisUtils.del("token:" + token);
		//将新token存入redis
		long nowTime = DateTime.now().getTime();
		String newTokenKey = "token:" + newToken;
		redisUtils.set(newTokenKey, newToken, (expireTime - nowTime) / 1000);
		return Result.success(tokenVo).setMessage("刷新token成功");
	}

	@GetMapping("/getInfo")
	public Result getUserInfo() {
		//从security上下文中获取用户信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ( authentication == null ) {
			return Result.fail().setMessage("用户未登录").setCode(ResultCode.UNAUTHORIZED);
		}
		User user = (User) authentication.getPrincipal();
		Object[] roles = roleService.selectRoleNameListByUserId(user.getId()).toArray();
		UserInfoVo userInfoVo = new UserInfoVo(user.getId(), user.getUsername(), user.getAvatar(), user.getNickname(), roles);
		return Result.success(userInfoVo).setMessage("获取用户信息成功");
	}


	/**
	 * 获取用户菜单列表
	 *
	 * @return Result 当前用户的菜单列表
	 */
	@GetMapping("/menuList")
	public Result getMenuList() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ( authentication == null ) {
			return Result.fail().setMessage("用户未登录").setCode(ResultCode.UNAUTHORIZED);
		}
		User user = (User) authentication.getPrincipal();
		List<Permission> permissions = user.getPermissions();
		//构建路由菜单, 并将permission_type为2的'按钮'移除, 保留菜单和其子菜单
		permissions.removeIf(permission -> permission.getPermissionType() == 2);
		List<RouteVo> routeVos = RouteTreeUtils.buildRouteTree(permissions, 0);
		return Result.success(routeVos).setMessage("获取菜单列表成功");
	}

	@PostMapping("/logout")
	public Result logout( HttpServletRequest request , HttpServletResponse response) {
		String token = request.getHeader("token");
		if ( StrUtil.isEmpty(token) ) {
			token = request.getParameter("token");
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ( authentication != null ) {
			redisUtils.del("token:" + token);   //登出需删除redis中的token
			SecurityContextLogoutHandler securityContextHolder = new SecurityContextLogoutHandler();
			securityContextHolder.logout(request,response,authentication);
			return Result.success().setMessage("退出登录成功");
		}
		return Result.fail().setMessage("用户未登录");
	}
}
