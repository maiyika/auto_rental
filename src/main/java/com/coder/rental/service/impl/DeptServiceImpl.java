package com.coder.rental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coder.rental.entity.Dept;
import com.coder.rental.mapper.DeptMapper;
import com.coder.rental.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coder.rental.utils.DeptTreeUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	/**
	 * 查询部门列表
	 *
	 * @param dept 查询条件
	 * @return 部门列表(stack递进增加)  1.查询顶级部门  2.查询子部门
	 */
	@Override
	public List<Dept> selectList( Dept dept ) {
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StrUtil.isNotEmpty(dept.getDeptName()),
				"dept_name", dept.getDeptName());
		queryWrapper.eq("pid", 0);    //查询顶级部门
		queryWrapper.orderByAsc("order_num");
		List<Dept> list = baseMapper.selectList(queryWrapper);
		Stack<Dept> stack = new Stack<>();
		for ( Dept dept1 : list ) {
			stack.push(dept1);
		}
		//todo:  减少数据库查询次数, 可以层级查询
		while ( !stack.empty() ) {
			Dept dept1 = stack.pop();
			QueryWrapper<Dept> queryById = new QueryWrapper<>();
			queryById.eq("pid", dept1.getId());
			List<Dept> deptList = baseMapper.selectList(queryById);
			dept1.setChildren(deptList);
			for ( Dept dept2 : deptList ) {
				stack.push(dept2);
			}
		}
		//查询每个部门的子部门
		return list;
	}

	@Override
	public List<Dept> selectTree() {
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByAsc("order_num");
		List<Dept> list = baseMapper.selectList(queryWrapper);
		Dept root = new Dept();
		root.setDeptName("所有部门").setId(0).setPid(-1);
		list.add(0, root);
		return DeptTreeUtils.buildTree(list, -1);
	}

	@Override
	public boolean selectChildren( Integer id ) {
		//查询是否有子部门
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", id);
		return baseMapper.selectCount(queryWrapper) > 0;
	}

}
