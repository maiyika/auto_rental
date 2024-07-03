package com.coder.rental.service;

import com.coder.rental.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
public interface IDeptService extends IService<Dept> {
	List<Dept>  selectList( Dept dept);
	List<Dept> selectTree();

	boolean selectChildren( Integer id );
}
