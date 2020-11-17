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
	public SalaryVO setSalaryVO(SalaryVO salary, ScheduleVO schedule) {

		Calendar[] standardTime = getStandardTime(schedule);
		salary.setSa_base(getBase(standardTime));
		salary.setSa_nextra(getNightExtra(standardTime));
		salary.setSa_oextra(getOverExtra(standardTime));
		salary.setSa_hextra(getHolidayExtra(standardTime));

		return salary;
	}

	public Calendar[] getStandardTime(ScheduleVO schedule) {

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

		return cal;
	}

	public double getBase(Calendar[] standardTime) {

		long millis = standardTime[1].getTimeInMillis() - standardTime[0].getTimeInMillis();

		double hours = (double) millis / 1000 / 60 / 60;
		hours = Math.round(hours * 100) / 100.0;

		return hours;
	}

	public int getNightExtra(Calendar[] standardTime) {

		return 0;
	}

	public int getOverExtra(Calendar[] standardTime) {

		return 0;
	}

	public int getHolidayExtra(Calendar[] standardTime) {

		return 0;
	}
}
