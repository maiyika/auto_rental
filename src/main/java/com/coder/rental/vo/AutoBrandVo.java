package com.coder.rental.vo;

import com.coder.rental.entity.AutoBrand;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Barry
 * @project auto_rental
 * @date 27/6/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AutoBrandVo {
	private String voId;
	private AutoBrand autoBrand;
	private String makerName;
}
