package com.albago.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {

	public static void main(String args[]) throws ParseException {

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Calendar calSrStart = Calendar.getInstance();
		Calendar calSrEnd = Calendar.getInstance();

		calSrStart.setTime(timeFormat.parse("03:00")); // 근무 시작 시간
		calSrEnd.setTime(timeFormat.parse("15:00")); // 근무 종료 시간

		if (calSrEnd.before(calSrStart)) {
			calSrEnd.add(Calendar.DATE, 1); // 근무 종료 시간이 근무 시작 시간보다 빠를 때 하루 더해줌
		}

		System.out.println(calSrStart.getTime());
		System.out.println(calSrEnd.getTime());
	}
}
