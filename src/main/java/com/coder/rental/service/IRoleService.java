package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
public interface IRoleService extends IService<Role> {
	public List<String> selectRoleNameListByUserId( Integer userId );

	public List<String> selectRoleNameListByPermissionId( Integer permissionId );

	Page<Role> selectList( Page<Role> page, Role role );
}
