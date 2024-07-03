package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoBrand;
import com.coder.rental.mapper.AutoBrandMapper;
import com.coder.rental.service.IAutoBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coder.rental.vo.AutoBrandVo;
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
public class AutoBrandServiceImpl extends ServiceImpl<AutoBrandMapper, AutoBrand> implements IAutoBrandService {

	/**
	 * 分页搜索
	 * @param page      分页对象(初始了当前页码和每页显示的记录数)
	 * @param autoBrand 查询对象
	 * @return 分页数据
	 */
	@Override
	public Page<AutoBrandVo> searchByPage( Page<AutoBrandVo> page, AutoBrand autoBrand ) {
		return baseMapper.searchByPage(page, autoBrand);
	}

	@Override
	public boolean hasChildren( Integer id ) {
		QueryWrapper<AutoBrand> wrapper = new QueryWrapper<>();
		wrapper.eq("mid", id);
		return baseMapper.selectCount(wrapper) > 0;
	}
}
