package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coder.rental.vo.AutoBrandVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
public interface IAutoBrandService extends IService<AutoBrand> {
	Page<AutoBrandVo> searchByPage( Page<AutoBrandVo> page, AutoBrand autoBrand );

	boolean hasChildren( Integer id );
}
