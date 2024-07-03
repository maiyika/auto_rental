package com.coder.rental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoMaker;
import com.coder.rental.mapper.AutoMakerMapper;
import com.coder.rental.service.IAutoMakerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@Service
public class AutoMakerServiceImpl extends ServiceImpl<AutoMakerMapper, AutoMaker> implements IAutoMakerService {

	@Override
	public Page<AutoMaker> search( int start, int size, AutoMaker autoMaker ) {
		QueryWrapper<AutoMaker> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByAsc("order_letter")
				//如果厂商名称不为空，则按照厂商名称进行模糊查询; 若为空，则查询全部
				.like(StrUtil.isNotBlank(autoMaker.getName()), "name", autoMaker.getName());
		Page<AutoMaker> page = new Page<>(start, size);
		return page(page, queryWrapper);
	}


}
