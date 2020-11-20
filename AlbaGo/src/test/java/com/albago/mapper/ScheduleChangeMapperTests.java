package com.albago.mapper;

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
public class ScheduleChangeMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ScheduleChangeMapper mapper;

//	@Test
	public void testInsert() {
		log.info("testInsert........................");

		ScheduleChangeVO scheduleChange = new ScheduleChangeVO();
		scheduleChange.setS_code(2022);
		scheduleChange.setSc_start("2020-11-26 20:00:00");
		scheduleChange.setSc_end("2020-11-27 06:00:00");
		scheduleChange.setSc_reason("제발 집좀 보내줘라 팀장아");

		log.info("Result_testInsert: " + mapper.insert(scheduleChange));
	}

//	@Test
	public void testCheckExist() {
		log.info("testCheckExist.......................");

		log.info("Result_testCheckExist: " + mapper.checkExist(2022));
	}

//	@Test
	/*
	 * public void testUpdate() { log.info("testUpdate........................");
	 * 
	 * ScheduleChangeVO scheduleChange = new ScheduleChangeVO();
	 * scheduleChange.setS_code(2022);
	 * scheduleChange.setSc_start("2020-11-26 22:00:00");
	 * scheduleChange.setSc_end("2020-11-27 08:00:00");
	 * scheduleChange.setSc_reason("제발 집좀 보내줘라 팀장아");
	 * 
	 * log.info("Result_testUpdate: " + mapper.update(scheduleChange)); }
	 */

//	@Test
	public void testSelect() {
		log.info("testSelect............................");

		log.info("Result_testCheckExist: " + mapper.select(2022));
	}

	@Test
	public void testDelete() {
		log.info("testDelete......................");

		log.info("Result_testDelete: " + mapper.delete(2022));
	}
}
