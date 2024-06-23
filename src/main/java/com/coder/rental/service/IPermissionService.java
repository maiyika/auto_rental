package com.coder.rental.service;

import com.coder.rental.entity.Permission;
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
public interface IPermissionService extends IService<Permission> {
	List<Permission> selectPermissionListByUserId(Integer userId);
}
