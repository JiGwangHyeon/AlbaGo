package com.albago.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.ScheduleChangeVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ScheduleChangeServiceTests {

	@Setter(onMethod_ = @Autowired)
	private ScheduleChangeService service;

//	@Test
	public void testIsWritable() {
		log.info("testIsWritable..............................");

		log.info("Result_testIsWritable: " + service.isWritable(2202));
	}

//	@Test
	public void testRequestChangeSchedule() {
		log.info("testRequestChangeSchedule.................");

		ScheduleChangeVO scheduleChange = new ScheduleChangeVO();
		scheduleChange.setS_code(2202);
		scheduleChange.setSc_start("2020-12-24 10:00:00");
		scheduleChange.setSc_end("2020-12-24 16:00:00");
		scheduleChange.setSc_reason("제발 집좀 보내줘라 팀장아");

		log.info("Result_testRequestChangeSchedule: " + service.applyChangeSchedule(scheduleChange));
	}

//	@Test
	/*
	 * public void testModifyRequest() {
	 * log.info("testModifyRequest........................");
	 * 
	 * ScheduleChangeVO scheduleChange = new ScheduleChangeVO();
	 * scheduleChange.setS_code(2022);
	 * scheduleChange.setSc_start("2020-12-24 22:00:00");
	 * scheduleChange.setSc_end("2020-12-25 08:00:00");
	 * scheduleChange.setSc_reason("제발 집좀 보내줘라 팀장아");
	 * 
	 * log.info("Result_testModifyRequest: " +
	 * service.modifyRequest(scheduleChange)); }
	 */

//	@Test
	public void testGetRequest() {
		log.info("testGetRequest..........................");

		log.info("Result_testGetRequest: " + service.getApplySingle(2022));
	}

	@Test
	public void testCancelRequest() {
		log.info("testCancelRequest.........................");

		log.info("Result_testCancelRequest: " + service.cancelRequest(2022));
	}
}
