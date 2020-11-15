package com.albago.mapper;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.ScheduleVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ScheduleMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ScheduleMapper mapper;

//	@Test
	public void testGetTodaysSchedule() {
		log.info("testGetTodaysSchedule..............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);

		log.info("ScheduleVO: " + mapper.getTodaysScheduleForE(schedule));
	}

//	@Test
	public void testArrive() {
		log.info("testArrive..................");

		log.info("Schedule_arrive: " + mapper.arrive(1));
	}

//	@Test
	public void testLeave() {
		log.info("testLeave.................");

		log.info("Schedule_leave: " + mapper.leave(1));
	}

//	@Test
	public void testGetWeekScheduleForE() {
		log.info("testGetWeekScheduleForE........................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setWeek(0);
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);

		log.info("schedule_getWeekScheduleForE: " + mapper.getWeekScheduleForE(schedule));
	}

//	@Test
	public void testGetMonthScheduleForE() {
		log.info("testGetMonthScheduleForE");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setYear("2020");
		schedule.setMonth("12");
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);

		log.info("schedule_getMonthScheduleForE: " + mapper.getMonthScheduleForE(schedule));

	}

//	@Test
	public void testInsertOnce() {
		log.info("testInsertOnce.....................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);
		schedule.setS_start("2020-11-15");
		schedule.setS_end("2020-11-15");

		log.info("schedule_insertOnce: " + mapper.insertOnce(schedule));
	}
}
