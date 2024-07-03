package com.coder.rental.controller;

import com.coder.rental.entity.Dept;
import com.coder.rental.service.IDeptService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/rental/dept")
public class DeptController {
	@Resource
	private IDeptService deptService;

	@PostMapping("/search")
	public Result list(@RequestBody Dept dept ) {
		return Result.success().setData(deptService.selectList(dept));
	}
	@PostMapping("/save")
	public Result save(@RequestBody Dept dept ) {
		boolean isSave = deptService.save(dept);
		return isSave ? Result.success() : Result.fail();
	}
	@PutMapping("/update")
	public Result update(@RequestBody Dept dept ) {
		boolean isUpdate = deptService.updateById(dept);
		return isUpdate ? Result.success() : Result.fail();
	}
	@DeleteMapping("/delete/{id}")
	public Result delete(@PathVariable Integer id ) {
		boolean isRemove = deptService.removeById(id);
		return isRemove ? Result.success() : Result.fail();
	}
	@GetMapping("/tree")
	public Result tree() {
		List<Dept> list = deptService.selectTree();
		return Result.success().setData(list);
	}

	@GetMapping("/children/{id}")
	public Result children(@PathVariable Integer id) {
		boolean b = deptService.selectChildren(id);
		return b ? Result.success().setMessage("yes") : Result.success().setMessage("no");
	}
}
