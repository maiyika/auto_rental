package com.coder.rental.controller;

import com.coder.rental.entity.Permission;
import com.coder.rental.service.IPermissionService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/rental/permission")
public class PermissionController {
	@Resource
	private IPermissionService permissionService;

	@GetMapping("/list")
	public Result list() {
		return Result.success(permissionService.selectList());
	}

	@GetMapping("/tree")
	public Result tree() {
		return Result.success(permissionService.selectTree());
	}

	@PostMapping("/save")
	public Result save( @RequestBody Permission permission ) {
		Integer permissionType = permission.getPermissionType();
		if ( permissionType != 2 ) {
			String routeUrl = permission.getRouteUrl();
			if ( routeUrl.contains("/") ) {
				permission.setRouteName(routeUrl.substring(routeUrl.lastIndexOf("/") + 1));
				permission.setRoutePath(routeUrl.substring(routeUrl.lastIndexOf("/")));
			}
		}
		boolean save = permissionService.save(permission);
		return save ? Result.success() : Result.fail();
	}

	@PutMapping("/update")
	public Result update( @RequestBody Permission permission ) {
		Integer permissionType = permission.getPermissionType();
		if ( permissionType != 2 ) {
			String routeUrl = permission.getRouteUrl();
			if ( routeUrl.contains("/") ) {
				permission.setRouteName(routeUrl.substring(routeUrl.lastIndexOf("/") + 1));
				permission.setRoutePath(routeUrl.substring(routeUrl.lastIndexOf("/")));
			}
		}
		boolean update = permissionService.updateById(permission);
		return update ? Result.success() : Result.fail();
	}

	@DeleteMapping("/delete/{id}")
	public Result delete( @PathVariable Integer id ) {
		boolean remove = permissionService.removeById(id);
		return remove ? Result.success() : Result.fail();
	}

	@GetMapping("/children/{id}")
	public Result hasChildren( @PathVariable Integer id ) {
		boolean b = permissionService.hasChildren(id);
		return b ? Result.success().setMessage("yes") : Result.success().setMessage("no");
	}
}
