package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coder.rental.entity.Permission;
import com.coder.rental.mapper.PermissionMapper;
import com.coder.rental.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coder.rental.utils.RouteTreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

	@Override
	public List<Permission> selectPermissionListByUserId( Integer userId ) {
		return baseMapper.selectPermissionListByUserId(userId);
	}

	@Override
	public List<Permission> selectList() {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByAsc("order_num");
		List<Permission> permissions = baseMapper.selectList(queryWrapper);
		return RouteTreeUtils.buildMenuTree(permissions, 0);
	}

	@Override
	public List<Permission> selectTree() {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.ne("permission_type", 2);
		queryWrapper.orderByAsc("order_num");
		List<Permission> permissions = baseMapper.selectList(queryWrapper);
		Permission root = new Permission();
		root.setId(0).setPid(-1).setPermissionLabel("根目录");
		permissions.add(root);
		return RouteTreeUtils.buildMenuTree(permissions, -1);
	}

	@Override
	public boolean hasChildren( Integer id ) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", id);
		return baseMapper.selectCount(queryWrapper) > 0;
	}
}
