package com.coder.rental.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoBrand;
import com.coder.rental.service.IAutoBrandService;
import com.coder.rental.utils.Result;
import com.coder.rental.vo.AutoBrandVo;
import jakarta.annotation.Resource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/rental/autoBrand")
public class AutoBrandController {
	@Resource
	private IAutoBrandService autoBrandService;

	@PostMapping("/{start}/{size}")
	public Result search( @PathVariable int start,
	                      @PathVariable int size,
	                      @RequestBody AutoBrand autoBrand ) {
		//System.out.println(autoBrand);
		Page<AutoBrandVo> page = new Page<>(start, size);
		return Result.success(autoBrandService.searchByPage(page, autoBrand));
	}
	@PostMapping("/save")
	public Result save( @RequestBody AutoBrand autoBrand ) {
		boolean isSaved = autoBrandService.save(autoBrand);
		return isSaved ? Result.success() : Result.fail();
	}

	@PutMapping("/update")
	public Result update( @RequestBody AutoBrand autoBrand ) {
		boolean isUpdated = autoBrandService.updateById(autoBrand);
		return isUpdated ? Result.success() : Result.fail();
	}

	@DeleteMapping("/delete/{ids}")
	public Result delete( @PathVariable String ids ) {
		List<Integer> list= Stream.of(ids.split(",")).map(Integer::parseInt).toList();
		boolean isRemoved = autoBrandService.removeByIds(list);
		return isRemoved ? Result.success() : Result.fail();
	}

	@GetMapping("hasChildren/{id}")
	public Result hasChildren(@PathVariable Integer id) {
		boolean b = autoBrandService.hasChildren(id);
		return b ? Result.success().setMessage("yes") : Result.success().setMessage("no");
	}
}
