package com.coder.rental.utils;

import cn.hutool.extra.pinyin.PinyinUtil;

/**
 * @author Barry
 * @project auto_rental
 * @date 26/6/2024
 */
public class PinYinUtils {
	public static String getPinYin( String chinese ) {
		return PinyinUtil.getFirstLetter(chinese,"").toUpperCase();
	}

}
