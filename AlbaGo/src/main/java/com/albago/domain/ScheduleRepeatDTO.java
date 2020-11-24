package com.albago.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Data;

@Data
public class ScheduleRepeatDTO {

	private int sr_code;
	private long c_code;
	private String u_id;
	private String sr_start;
	private String sr_end;
	private String sr_repeat;
	private char sr_stat;
	private String u_name;
	private String startDate;

}

class method {
	public String getDate(String[] day, String date, String startTime, String format) {

		Calendar now = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		SimpleDateFormat sdf_return = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date time = new Date();

		try {
			time = sdf.parse(startTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);

		for (String i : day) {

			int parseInt = Integer.parseInt(i);

			Calendar cal = (Calendar) now.clone();
			cal.setTime(time);

			if (parseInt == dayOfWeek) {
				if (cal.after(now)) {
					return getTime(cal, parseInt, sdf_return);
				}
			} else if (Integer.parseInt(i) > dayOfWeek) {
				return getTime(cal, parseInt, sdf_return);
			}

		}

		now.setTime(time);
		now.add(Calendar.WEEK_OF_YEAR, 1);
		now.set(Calendar.DAY_OF_WEEK, Integer.parseInt(day[0]));

		return getTime(now, Integer.parseInt(day[0]), sdf_return);

	}

	public String getTime(Calendar cal, int parseInt, SimpleDateFormat sdf_return) {
		cal.set(Calendar.DAY_OF_WEEK, parseInt);
		return sdf_return.format(cal.getTime());
	}
}
