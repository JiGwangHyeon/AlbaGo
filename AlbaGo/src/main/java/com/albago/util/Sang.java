package com.albago.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import lombok.Data;

public class Sang {

}

@Data
class ScheduleRepeatDTO {

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

//mapper

interface ScheduleRepeatMapper {

	public List<ScheduleRepeatDTO> selectScheduleRepeatWaiting(ScheduleRepeatDTO scheduleRepeatDTO);

}

/*
 * SELECT sr_code, c_code, u_id, sr_start, sr_end, sr_repeat, sr_stat,( SELECT
 * u_name FROM
 * 
 * userinfo u WHERE u.u_id=s.u_id) u_name FROM schedule_repeat s WHERE sr_stat =
 * 'W';
 */

interface ScheduleRepeatService {
	public List<ScheduleRepeatDTO> getWaitingRepeatList(ScheduleRepeatDTO scheduleRepeatDTO);
}

class ScheduleRepeatServiceImpl {
	public List<ScheduleRepeatDTO> getWaitingRepeatList(ScheduleRepeatDTO scheduleRepeatDTO) {

		List<ScheduleRepeatDTO> list = mapper.selectScheduleRepeatWaiting();
		String[] day;
		String format = "HH:mm";

		for (ScheduleRepeatDTO l : list) {
			String sr_repeat = l.getSr_repeat();
			day = (sr_repeat == null) ? new String[] {} : sr_repeat.split("");
			l.setStartDate(method.getDate(day, l.getSr_start(), format));
		}

		return list;
	}
}

class method {

	public static String getDate(String[] day, String startTime, String format) {

		Calendar now = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		SimpleDateFormat sdf_return = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Calendar time = Calendar.getInstance();

		try {
			time.setTime(sdf.parse(startTime));
			System.out.println(time.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);

		Calendar clone = (Calendar) now.clone();
		clone.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
		clone.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
		clone.set(Calendar.SECOND, time.get(Calendar.SECOND));

		for (String i : day) {

			int parseInt = Integer.parseInt(i);

			Calendar cal = (Calendar) clone.clone();
			cal.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
			cal.set(Calendar.SECOND, time.get(Calendar.SECOND));
			System.out.println(cal.getTime());

			if (parseInt == dayOfWeek) {
				if (cal.after(now)) {
					return getTime(cal, parseInt, sdf_return);
				}
			} else if (Integer.parseInt(i) > dayOfWeek) {
				return getTime(cal, parseInt, sdf_return);
			}

		}

		System.out.println(clone.getTime());
		clone.add(Calendar.WEEK_OF_YEAR, 1);
		System.out.println(clone.getTime());
		clone.set(Calendar.DAY_OF_WEEK, Integer.parseInt(day[0]));
		System.out.println(clone.getTime());

		return getTime(clone, Integer.parseInt(day[0]), sdf_return);

	}

	public static String getTime(Calendar cal, int parseInt, SimpleDateFormat sdf_return) {
		cal.set(Calendar.DAY_OF_WEEK, parseInt);
		return sdf_return.format(cal.getTime());
	}
}
