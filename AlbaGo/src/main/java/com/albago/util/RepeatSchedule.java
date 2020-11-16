package com.albago.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;
import com.albago.service.ScheduleRepeatService;
import com.albago.service.ScheduleService;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class RepeatSchedule {

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ScheduleRepeatService scheduleRepeatService;

//	@Scheduled(cron = "0 0 0 1 * ?")
	public void insertMonthly() {

	}

	public void whenInsert(ScheduleRepeatVO scheduleRepeat, ScheduleService schedule) throws ParseException {

		int c_code = scheduleRepeat.getC_code();
		String u_id = scheduleRepeat.getU_id();
		String sr_start = scheduleRepeat.getSr_start();
		String sr_end = scheduleRepeat.getSr_end();
		String sr_repeat = scheduleRepeat.getSr_repeat();

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date time_start = timeFormat.parse(sr_start);
		Date time_end = timeFormat.parse(sr_end);

		Calendar now = Calendar.getInstance();
		System.out.println("now: " + now.getTime());
		Calendar end = Calendar.getInstance();
		end.set(0, 0, 0, 0, 0, 0);
		end.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);
		end.add(Calendar.MONTH, 2);
		System.out.println("end: " + end.getTime());

		int interval = (int) (time_end.getTime() - time_start.getTime());
		interval = (interval < 0) ? 86400000 + interval : interval;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String[] dayArr = getDayArr(sr_repeat);

		ScheduleVO scheduleVo = new ScheduleVO();
		scheduleVo.setC_code(c_code);
		scheduleVo.setU_id(u_id);

		for (int i = 0; i < dayArr.length; i++) {
			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			System.out.println("calStart: " + calStart.getTime());
			System.out.println("calEnd: " + calEnd.getTime());

			calStart.setTime(time_start);
			System.out.println("now: " + now.getTime());
			calStart.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

			int dayArr_int = Integer.parseInt(dayArr[i]);

			if (now.get(Calendar.DAY_OF_WEEK) <= dayArr_int) {
				System.out.println("dayArr_int: " + dayArr_int);
				System.out.println("calStart: " + calStart.getTime());
				calStart.set(Calendar.DAY_OF_WEEK, dayArr_int);
//				System.out.println("calStart: " + calStart.getTime());
				calEnd = (Calendar) calStart.clone();
				calEnd.add(Calendar.MILLISECOND, interval);
				System.out.println("calEnd: " + calEnd.getTime());

				scheduleVo.setS_start(sdf.format(calStart.getTime()));
				scheduleVo.setS_end(sdf.format(calEnd.getTime()));

				schedule.insertSchedule(scheduleVo);

				calStart.add(Calendar.DAY_OF_MONTH, 7);
				calEnd.add(Calendar.DAY_OF_MONTH, 7);
			}
			while (end.after(calStart)) {
				scheduleVo.setS_start(sdf.format(calStart.getTime()));
				scheduleVo.setS_end(sdf.format(calEnd.getTime()));
				System.out.println("calStart: " + calStart.getTime());
				System.out.println("calEnd: " + calEnd.getTime());

				schedule.insertSchedule(scheduleVo);

				calStart.add(Calendar.DAY_OF_MONTH, 7);
				calEnd.add(Calendar.DAY_OF_MONTH, 7);
			}
		}
	}

	public String[] getDayArr(String repeat) {
		return repeat == null ? new String[] {} : repeat.split("");
	}

}
