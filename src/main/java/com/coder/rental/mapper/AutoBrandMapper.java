package com.coder.rental.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coder.rental.vo.AutoBrandVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
public interface AutoBrandMapper extends BaseMapper<AutoBrand> {
	Page<AutoBrandVo> searchByPage( @Param("page") Page<AutoBrandVo> page,
	                                @Param("autoBrand") AutoBrand autoBrand);
}
