package com.coder.rental.mapper;

import com.coder.rental.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
public interface RoleMapper extends BaseMapper<Role> {
	public List<String> selectRoleNameListByUserId(Integer userId);
}
