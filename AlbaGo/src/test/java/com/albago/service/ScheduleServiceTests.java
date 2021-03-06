package com.albago.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.HistoryVO;
import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;
import com.albago.mapper.HistoryMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ScheduleServiceTests {

	@Setter(onMethod_ = @Autowired)
	private ScheduleService service;

	@Setter(onMethod_ = @Autowired)
	private HistoryMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private ScheduleService scheduleService;

//	@Test
	public void testGetTodaysSchedule() {
		log.info("testGetTodaysSchedule................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);

		log.info("ScheduleVO: " + service.getTodaysSchedule(schedule));
	}

//	@Test
	public void testGetSchedule() {
		log.info("testGetSchedule........................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setS_code(2302);

		log.info("ScheduleVO: " + service.getScheduleSingle(schedule));
	}

//	@Test
	public void testArrive() {
		log.info("testArrive............................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(1234123521);
		schedule.setS_code(1);
		schedule.setU_id("kwang2951");

		log.info("Schedule_arrive: " + service.arrive(schedule));
	}

//	@Test
	public void testLeave() {
		log.info("testLeave............................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(1234123521);
		schedule.setS_code(1);
		schedule.setU_id("kwang2951");

		log.info("Schedule_arrive: " + service.leave(schedule));
	}

//	@Test
	public void testGetWeekScheduleForE() {
		log.info("testGetWeekScheduleForE........................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);

		Calendar s_start = Calendar.getInstance();

		log.info(s_start.getTime());

		if (s_start.get(Calendar.DAY_OF_WEEK) == 1) {
			s_start.add(Calendar.DATE, -6 + 0 * 7);
			log.info(s_start.getTime());
		} else {
			s_start.add(Calendar.DATE, -s_start.get(Calendar.DAY_OF_WEEK) + 2 + 0 * 7);
			log.info(s_start.getTime());
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		schedule.setS_start(sdf.format(s_start.getTime()));

		log.info("schedule_getWeekScheduleForE: " + service.getScheduleWeek(schedule, week));
	}

//	@Test
	public void testGetMonthScheduleForE() {
		log.info("testGetMonthScheduleForE");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);

		Calendar s_start = Calendar.getInstance();
		s_start.set(Calendar.YEAR, 2020);
		s_start.set(Calendar.MONTH, 10);
		log.info(s_start.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		schedule.setS_start(sdf.format(s_start.getTime()));

		log.info("schedule_getMonthScheduleForE: " + service.getScheduleMonth(schedule, year, month));

	}

//	@Test
	public void testInsertOnce() {
		log.info("testInsertOnce.....................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setU_id("yu0372");
		schedule.setC_code(1234567890);
		schedule.setS_start("2020-11-18 09:00:00");
		schedule.setS_end("2020-11-18 18:00:00");

		log.info("schedule_getMonthScheduleForE: " + service.applySchedule(schedule));

	}

//	@Test
	public void testDummy() {

		List<HistoryVO> list = mapper.makeDummy();
		for (HistoryVO hvo : list) {
			ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

			scheduleRepeat.setC_code(hvo.getC_code());
			scheduleRepeat.setU_id(hvo.getU_id());
			scheduleRepeat.setSr_start("22:00:00");
			scheduleRepeat.setSr_end("08:00:00");
			scheduleRepeat.setSr_repeat("56");

			int result = scheduleRepeatService.applyRepeatedSchedule(scheduleRepeat);

			if (result == 1) {
				RepeatSchedule rs = new RepeatSchedule();
				try {
					result = rs.whenInsert(scheduleRepeat, scheduleService);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

//	@Test
	public void test() {
		int sr_code = 282;

		service.permitAppliedRepeatedSchedule(sr_code);
	}

	@Test
	public void dummy() {

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setC_code(1324132413);
		scheduleRepeat.setU_id("fGTIBn52");
		scheduleRepeat.setSr_start("09:00");
		scheduleRepeat.setSr_end("17:00");
		scheduleRepeat.setSr_repeat("23456");

		service.insertDummy(scheduleRepeat);
	}
}
