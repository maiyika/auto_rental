package com.coder.rental.vo;

import com.coder.rental.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Barry
 * @project auto_rental
 * @date 3/7/2024
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionVo {
	private Object[] checkedList;   //原有权限id数组
	private List<Permission> permissionList;  //所有权限列表
}
