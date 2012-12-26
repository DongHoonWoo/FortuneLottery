package com.happyty.fortunelottery.util;

public class StringUtils {
	public static int toInt(String str, int def) {
		try {
			def = Integer.parseInt(str);
		} catch (Exception e) {
		}
		return def;
	}
}
