package com.coder.rental.service.impl;

import com.coder.rental.entity.Role;
import com.coder.rental.mapper.RoleMapper;
import com.coder.rental.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	@Override
	public List<String> selectRoleNameListByUserId( Integer userId ) {
		return baseMapper.selectRoleNameListByUserId(userId);
	}
}
