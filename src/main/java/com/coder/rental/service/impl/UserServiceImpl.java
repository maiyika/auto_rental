package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coder.rental.entity.User;
import com.coder.rental.mapper.UserMapper;
import com.coder.rental.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Override
	public User selectByUsername( String username ) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(); //创建查询条件对象
		queryWrapper.eq("username", username); //设置查询条件
		return baseMapper.selectOne(queryWrapper);
	}
}
