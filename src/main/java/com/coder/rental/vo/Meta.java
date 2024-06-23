package com.coder.rental.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Barry
 * @project auto_rental
 * @date 23/6/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meta {
	private String title;
	private String icon;
	private Object[] roles;
}
