package com.albago.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.ScheduleRepeatVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ScheduleRepeatServiceTests {

	@Setter(onMethod_ = @Autowired)
	private ScheduleService service;

	@Test
	public void testInsert() {
		log.info("testInsert............................");

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setC_code(1234567890);
		scheduleRepeat.setU_id("kwang2951");
		scheduleRepeat.setSr_start("12:00");
		scheduleRepeat.setSr_end("18:00");
		scheduleRepeat.setSr_repeat("0134");

		log.info("testInsert_result: " + service.applyRepeatedSchedule(scheduleRepeat));
	}

//	@Test
	public void testcheckDuplicateRepeat() {
		log.info("testCheck............................");

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setC_code(1234123521);
		scheduleRepeat.setU_id("a");
		scheduleRepeat.setSr_start("13:00:00");
		scheduleRepeat.setSr_end("15:00:00");
		scheduleRepeat.setSr_repeat("7");

	}
}
