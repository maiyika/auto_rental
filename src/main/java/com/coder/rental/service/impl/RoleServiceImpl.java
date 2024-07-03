package com.coder.rental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Role;
import com.coder.rental.entity.RolePermission;
import com.coder.rental.entity.User;
import com.coder.rental.entity.UserRole;
import com.coder.rental.mapper.RoleMapper;
import com.coder.rental.mapper.RolePermissionMapper;
import com.coder.rental.mapper.UserMapper;
import com.coder.rental.mapper.UserRoleMapper;
import com.coder.rental.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private UserRoleMapper userRoleMapper;

	@Resource
	private RolePermissionMapper rolePermissionMapper;
	@Override
	public List<String> selectRoleNameListByUserId( Integer userId ) {
		return baseMapper.selectRoleNameListByUserId(userId);
	}

	@Override
	public List<String> selectRoleNameListByPermissionId( Integer permissionId ) {
		return baseMapper.selectRoleNameListByPermissionId(permissionId);
	}

	/**
	 * 分页查询角色列表
	 *
	 * @param page 分页对象
	 * @param role 角色对象
	 * @return 当前搜索对象可管理的角色
	 */
	@Override
	public Page<Role> selectList( Page<Role> page, Role role ) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StrUtil.isNotEmpty(role.getRoleName()), "role_name", role.getRoleName());
		queryWrapper.orderByAsc("create_time");
		Integer userId = role.getCreaterId();
		User user = userMapper.selectById(userId);
		if ( user != null && user.getIsAdmin() != null && !user.getIsAdmin() ) {        // 如果不是管理员, 只能查看自己创建的角色
			queryWrapper.eq("creater_id", userId);
		}
		return baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	public boolean hasUser( Integer roleId ) {
		QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("role_id", roleId);
		return userRoleMapper.selectCount(queryWrapper) > 0;
	}

	@Override
	public boolean deleteRole( String ids ) {
		Arrays.stream(ids.split(",")).map(Integer::parseInt).forEach(roleId -> {
			if ( !hasUser(roleId) ) {
				// 删除角色权限关联, 当没有关联用户时, 删除角色以及关联表中的数据
				//删除当前角色在角色权限表中有关联的权限
				rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", roleId));
				baseMapper.deleteById(roleId);
			}
		});
		return true;
	}
}
