package com.coder.rental.utils;

import com.coder.rental.entity.Permission;
import com.coder.rental.service.IRoleService;
import com.coder.rental.vo.Meta;
import com.coder.rental.vo.RouteVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Barry
 * @project auto_rental
 * @date 23/6/2024
 */
@Component
public class RouteTreeUtils {

	@Resource
	private IRoleService roleService;

	/**
	 * 构建前端路由
	 * @param routes 权限列表-> 由当前登录用户所拥有的权限
	 * @param pid  父级id
	 * @return List<RouteVo> 生成前端路由(all)
	 */
	public List<RouteVo> buildRouteTree( List<Permission> routes, int pid ) {
		List<RouteVo> tree = new ArrayList<>();
		Optional.ofNullable(routes).orElse(new ArrayList<>())  //防止空指针
				.stream()
				.filter(permission ->
						permission != null && permission.getPid() == pid)
				.forEach(permission -> {
					RouteVo route = new RouteVo();
					route.setPath(permission.getRoutePath());
					route.setName(permission.getRouteName());
					if ( permission.getPid() == 0 ) {
						route.setComponent("Layout");
						route.setAlwaysShow(true);
					}  //根路径
					else {
						route.setComponent(permission.getRouteUrl());
						route.setAlwaysShow(false);
					}
					List<String> roleNames = roleService.selectRoleNameListByPermissionId(permission.getId());
					Meta meta = new Meta(permission.getPermissionLabel(),
							             permission.getIcon(),
							             roleNames.toArray());
					route.setMeta(meta);
					List<RouteVo> children = buildRouteTree(routes, permission.getId());
					//将当前权限id作为父级id,递归构建子路由
					route.setChildren(children);
					tree.add(route);
				});
		return tree;
	}

	public static List<Permission> buildMenuTree(List<Permission> list, int pid){
		List<Permission> res = new ArrayList<>();
		Optional.ofNullable(list).orElse(new ArrayList<>())
				.stream().filter(permission -> permission != null && permission.getPid() == pid)
				.forEach(permission -> {
					permission.setChildren(buildMenuTree(list, permission.getId()));
					res.add(permission);
				});
		return res;
	}
}
