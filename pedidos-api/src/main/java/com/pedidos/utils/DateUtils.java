package com.pedidos.utils;

import java.util.Date;

public class DateUtils {
	
	public static Date currentDate() { return new Date(); }
	
	public static Date endOfDay(final Date startOfDay) {
		return Date.from(startOfDay.toInstant().plusSeconds((3600 * 24) - 1));
	}
}
