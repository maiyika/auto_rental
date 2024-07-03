package com.coder.rental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Role;
import com.coder.rental.entity.User;
import com.coder.rental.mapper.RoleMapper;
import com.coder.rental.mapper.UserMapper;
import com.coder.rental.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
		if ( user != null && !user.getIsAdmin() ) {        // 如果不是管理员, 只能查看自己创建的角色
			queryWrapper.eq("creater_id", userId);
		}
		return baseMapper.selectPage(page, queryWrapper);
	}
}
