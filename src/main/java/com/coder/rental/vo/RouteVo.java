package com.coder.rental.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author Barry
 * @project auto_rental
 * @date 23/6/2024
 */
@Data
public class RouteVo {
	private String path;
	private String component;
	private String name;
	private Boolean alwaysShow;
	private Meta meta;
	private List<RouteVo> children;
}
