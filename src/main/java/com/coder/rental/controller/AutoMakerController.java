package com.coder.rental.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoMaker;
import com.coder.rental.service.IAutoMakerService;
import com.coder.rental.utils.PinYinUtils;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/rental/autoMaker")
public class AutoMakerController {
	@Resource
	private IAutoMakerService autoMakerService;

	/**
	 * 分页搜索
	 *
	 * @param start     当前页码
	 * @param size      每页显示的记录数
	 * @param autoMaker 查询对象(也就是前端输入的查询条件)
	 */
	@PostMapping("/{start}/{size}")
	public Result search( @PathVariable int start,
	                      @PathVariable int size,
	                      @RequestBody AutoMaker autoMaker ) {
		Page<AutoMaker> search = autoMakerService.search(start, size, autoMaker);
		return Result.success(search);
	}

	@DeleteMapping("/delete/{id}/{name}/{orderLetter}/{deleted}")
	public Result delete( @ModelAttribute AutoMaker autoMaker ) {
		// 根据autoMaker执行删除操作
		boolean isRemoved = autoMakerService.removeById(autoMaker.getId());
		return isRemoved ? Result.success() : Result.fail();
	}

	@PostMapping("/save")
	public Result save( @RequestBody AutoMaker autoMaker ) {
		// 根据autoMaker执行保存操作
		autoMaker.setOrderLetter(PinYinUtils.getPinYin(autoMaker.getName()));
		boolean isSaved = autoMakerService.save(autoMaker);
		return isSaved ? Result.success() : Result.fail();
	}

	@PutMapping("/update")
	public Result update( @RequestBody AutoMaker autoMaker ) {
		// 根据autoMaker执行更新操作
		autoMaker.setOrderLetter(PinYinUtils.getPinYin(autoMaker.getName()));
		boolean isUpdated = autoMakerService.updateById(autoMaker);
		return isUpdated ? Result.success() : Result.fail();
	}

}
