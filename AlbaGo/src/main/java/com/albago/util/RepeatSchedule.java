package com.albago.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.albago.domain.ScheduleVO;
import com.albago.service.ScheduleService;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class RepeatSchedule {

	@Autowired
	private ScheduleService scheduleService;

//	@Scheduled(cron = "0/3 * * * * *")
	public void testtask() {
		System.out.println("test중");
	}

	@Scheduled(cron = "0 0 0 1 * ?")
	public void schedule() {

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(1234123521);
		schedule.setU_id("gkdud0941");
		Calendar start_date = Calendar.getInstance();
		Calendar end_date = Calendar.getInstance();
		start_date.set(2020, 11, 20, 8, 0, 0);
		end_date.set(2020, 11, 20, 18, 0, 0);

		if (start_date.after(end_date)) {
			end_date.add(Calendar.DATE, 1);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		schedule.setS_start(sdf.format(start_date.getTime()));
		schedule.setS_end(sdf.format(end_date.getTime()));

		log.info("태스크 실행");
		scheduleService.insertSchedule(schedule);
	}

}
