package com.coder.rental.security;

import com.coder.rental.entity.Permission;
import com.coder.rental.entity.User;
import com.coder.rental.service.IPermissionService;
import com.coder.rental.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author Barry
 * @project auto_rental
 * @date 17/6/2024
 */

@Component
public class CustomerUserDetailsService implements UserDetailsService {

	@Resource
	private IUserService userService;
	@Resource
	private IPermissionService permissionService;

	/**
	 * 实现UserDetailsService接口的loadUserByUsername方法，用于加载用户信息
	 *
	 * @param username 用户名
	 * @return UserDetails 用户信息
	 * @throws UsernameNotFoundException 用户名未找到异常
	 */
	@Override
	public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
		User user = userService.selectByUsername(username);   //到数据库查询用户信息(用username查询)
		if ( user == null ) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		List<Permission> permissions = permissionService.selectPermissionListByUserId(user.getId());
		user.setPermissions(permissions);   //设置用户权限

		//通过steam流式处理，将权限列表转换为字符串列表
		List<String> list = permissions.stream().filter(Objects::nonNull)
				.map(Permission::getPermissionCode)
				.filter(Objects::nonNull)
				.toList();
		String[] array = list.toArray(new String[0]); //将字符串列表转换为字符串数组
		List<GrantedAuthority> authorityList=AuthorityUtils.createAuthorityList(array);
		user.setAuthorities(authorityList);  //设置用户权限
		//System.out.println("用户信息："+user);
		return user;
	}
}
