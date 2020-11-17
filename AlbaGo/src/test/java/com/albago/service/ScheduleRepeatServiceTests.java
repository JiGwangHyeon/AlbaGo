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
	private ScheduleRepeatService service;

	@Test
	public void testInsert() {
		log.info("testInsert............................");

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setC_code(1234123521);
		scheduleRepeat.setU_id("gkdud0941");
		scheduleRepeat.setSr_start("12:00:00");
		scheduleRepeat.setSr_end("18:00:00");
		scheduleRepeat.setSr_repeat("034");

		log.info("testInsert_result: " + service.insertRepeat(scheduleRepeat));
		log.info("sr_code: " + scheduleRepeat.getSr_code());
	}
}
