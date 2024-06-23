package com.coder.rental.service.impl;

import com.coder.rental.entity.Permission;
import com.coder.rental.mapper.PermissionMapper;
import com.coder.rental.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
