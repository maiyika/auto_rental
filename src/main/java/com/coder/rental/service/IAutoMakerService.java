package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoMaker;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
public interface IAutoMakerService extends IService<AutoMaker> {

	Page<AutoMaker> search( int start, int size, AutoMaker autoMaker );

}
