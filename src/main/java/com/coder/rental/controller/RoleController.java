package com.coder.rental.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Role;
import com.coder.rental.entity.User;
import com.coder.rental.service.IPermissionService;
import com.coder.rental.service.IRoleService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/rental/role")
public class RoleController {
	@Resource
	private IRoleService roleService;
	@Resource
	private IPermissionService permissionService;

	@PostMapping("/{start}/{size}")
	public Result search( @PathVariable int start,
	                      @PathVariable int size,
	                      @RequestBody Role role ) {
		//从SecurityContextHolder中获取当前用户信息, 也可以从token中获取useridrent
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		role.setCreaterId(user.getId());*/
		//前端传过来也行
		Page<Role> page = new Page<>(start, size);
		return Result.success(roleService.selectList(page, role));
	}

	@PutMapping
	public Result update( @RequestBody Role role ) {
		boolean b = roleService.updateById(role);
		return b ? Result.success() : Result.fail();
	}

	@DeleteMapping("/{ids}")
	public Result delete( @PathVariable String ids ) {
		boolean b = roleService.deleteRole(ids);
		return b ? Result.success() : Result.fail();
	}

	@PostMapping
	public Result save( @RequestBody Role role ) {
		boolean b = roleService.save(role);
		return b ? Result.success() : Result.fail();
	}

	@GetMapping("/hasUser/{id}")
	public Result hasUser( @PathVariable Integer id ) {
		boolean b = roleService.hasUser(id);
		return b ? Result.success().setMessage("yes") : Result.success().setMessage("no");
	}

	//获取菜单树  当前角色id, 用户id
	@GetMapping("/getRolePermissionTree/{roleId}/{userId}")
	public Result getRolePermissionTree( @PathVariable Integer roleId,
	                                    @PathVariable Integer userId ) {
		return Result.success(permissionService.selectPermissionTree(userId, roleId));
	}

}
