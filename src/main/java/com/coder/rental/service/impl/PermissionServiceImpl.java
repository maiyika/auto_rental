package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coder.rental.entity.Permission;
import com.coder.rental.entity.User;
import com.coder.rental.mapper.PermissionMapper;
import com.coder.rental.mapper.UserMapper;
import com.coder.rental.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coder.rental.utils.RouteTreeUtils;
import com.coder.rental.vo.RolePermissionVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

	@Resource
	private UserMapper userMapper;


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

	/**
	 * 查询权限树
	 * @param userId 用户id, 查询该用户权限
	 * @param roleId 角色id  查询该角色原有的权限
	 * @return RolePermissionVo 返回权限树以及该角色原有的权限集
	 */
	@Override
	public RolePermissionVo selectPermissionTree( Integer userId, Integer roleId ) {
		User user= userMapper.selectById(userId);
		//如果用户为管理员, 查询所有权限
		List<Permission> list = new ArrayList<>();
		if(user!=null && user.getIsAdmin()){
			list = baseMapper.selectList(null);
		}else{
			//否则查询当前用户所具有的权限
			list = baseMapper.selectPermissionListByUserId(userId);
		}
		//将权限列表转换为树形结构
		List<Permission> permissions = RouteTreeUtils.buildMenuTree(list, 0);
		//查询要分配角色原有的权限
		List<Permission> checkedList = baseMapper.selectPermissionListByRoleId(roleId);
		//找出包含部分, 放在数组中
		List<Permission> newList= new ArrayList<>(list);
		newList.retainAll(checkedList);   //交集
		Object[] array = newList.stream().map(Permission::getId).toArray();
		return new RolePermissionVo(array, permissions);
	}
}
