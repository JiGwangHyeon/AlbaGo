package com.albago.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.albago.domain.SalaryVO;
import com.albago.domain.ScheduleVO;

public class CalcDate {

	// 휴일
	// 토요일, 일요일, 3/1, 8/15, 10/3, 10/9, 1/1
	// 음력(12월 말일, 1/1, 2) 음력(4/8), 5/5
	// 6/6, 음력(8/14, 15, 16) 12/25
	public static SalaryVO setSalaryVO(SalaryVO salary, ScheduleVO schedule) {

		Calendar[] standardTime = getStandardTime(schedule);
		double base = getBase(standardTime);
		salary.setSa_base(getBase(standardTime));
		salary.setSa_nextra(getNightExtra(standardTime));
		salary.setSa_oextra(getOverExtra(base));
		salary.setSa_hextra(getHolidayExtra(standardTime, base));

		return salary;
	}

	public static Calendar[] getStandardTime(ScheduleVO schedule) {

		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Calendar arrive = Calendar.getInstance();
		Calendar leave = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			start.setTime(sdf.parse(schedule.getS_start()));
			end.setTime(sdf.parse(schedule.getS_end()));
			arrive.setTime(sdf.parse(schedule.getS_arrive()));
			leave.setTime(sdf.parse(schedule.getS_leave()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar[] cal = new Calendar[2];
		cal[0] = (start.after(arrive)) ? start : arrive;
		cal[1] = (end.after(leave)) ? leave : end;

		System.out.println("cal0: " + cal[0].getTime());
		System.out.println("cal1: " + cal[1].getTime());

		return cal;
	}

	public static double getBase(Calendar[] standardTime) {

		long millis = standardTime[1].getTimeInMillis() - standardTime[0].getTimeInMillis();

		double hours = (double) millis / 1000 / 60 / 60;
		hours = Math.round(hours * 100) / 100.0;

		return hours;
	}

	public static double getNightExtra(Calendar[] standardTime) {
		Calendar nStart = (Calendar) standardTime[0].clone();
		nStart.set(Calendar.HOUR_OF_DAY, 22);
		nStart.set(Calendar.MINUTE, 0);
		nStart.set(Calendar.SECOND, 0);
		Calendar nEnd = (Calendar) nStart.clone();
		nEnd.add(Calendar.HOUR_OF_DAY, 8);

		System.out.println();

		double nightExtra = 0;
		long millis = 0;
		if (nStart.after(standardTime[0])) {
			if (standardTime[1].after(nStart) && nEnd.after(standardTime[1])) {
				millis = standardTime[1].getTimeInMillis() - nStart.getTimeInMillis();
			} else if (nEnd.after(nStart) && nEnd.before(standardTime[1])) {
				millis = nEnd.getTimeInMillis() - nStart.getTimeInMillis();
			}
		} else if (standardTime[0].after(nStart)) {
			if (standardTime[1].after(standardTime[0]) && nEnd.after(standardTime[1])) {
				millis = standardTime[1].getTimeInMillis() - standardTime[0].getTimeInMillis();
			} else if (nEnd.after(standardTime[0]) && nEnd.before(standardTime[1])) {
				millis = nEnd.getTimeInMillis() - standardTime[0].getTimeInMillis();
			}
		}

		nightExtra = (double) millis / 1000 / 60 / 60;
		nightExtra = Math.round(nightExtra * 100) / 100.0;

		return nightExtra;
	}

	public static double getOverExtra(double base) {
		if (base > 8.0) {
			base -= 8;
		}
		base = Math.round(base * 100) / 100.0;

		return base;
	}

	public static double getHolidayExtra(Calendar[] standardTime, double base) {

		if (Holiday.isHoliday(standardTime[0])) {
			return base;
		}
		return 0.0;
	}

	public static double getWeeklyExtra(double sumOfBase, int count) {
		if (sumOfBase > 40) {
			return 8 / count;
		} else if (sumOfBase >= 15) {
			return sumOfBase / 5 / count;
		} else {
			return 0;
		}
	}
}
