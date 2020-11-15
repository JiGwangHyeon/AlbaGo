package com.albago.service;

import org.junit.Test;
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
public class ScheduleServiceTests {

	@Setter(onMethod_ = @Autowired)
	private ScheduleService service;

	@Test
	public void testGetTodaysSchedule() {
		log.info("testGetTodaysSchedule................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);

		log.info("ScheduleVO: " + service.getTodaysSchedule(schedule));
	}
}
