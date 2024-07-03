package com.coder.rental.utils;

import com.coder.rental.entity.Dept;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Barry
 * @project auto_rental
 * @date 29/6/2024
 */
public class DeptTreeUtils {
	public static List<Dept> buildTree( List<Dept> list, int pid ) {
		List<Dept> res = new ArrayList<>();
		Optional.ofNullable(list).orElse(new ArrayList<>())
				.stream().filter(dept -> dept != null && dept.getPid() == pid)
				.forEach(dept -> {
					dept.setChildren(buildTree(list, dept.getId()));
					res.add(dept);
				});
		return res;
	}
}
