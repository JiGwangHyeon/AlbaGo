package com.albago.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleVO;
import com.albago.mapper.ScheduleMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private ScheduleMapper mapper;

	@Override
	public ScheduleVO getTodaysScheduleForE(ScheduleVO schedule) {
		log.info("getTodaysSchedule................");

		return mapper.getTodaysScheduleForE(schedule);
	}

	@Override
	public int arrive(int s_code) {
		log.info("arrive................");

		return mapper.arrive(s_code);
	}

	@Override
	public int leave(int s_code) {
		log.info("leave................");

		return mapper.leave(s_code);
	}

	@Override
	public List<ScheduleVO> getWeekScheduleForE(ScheduleVO schedule) {
		log.info("getWeekScheduleForE...................");

		return mapper.getWeekScheduleForE(schedule);
	}

	@Override
	public List<ScheduleVO> getMonthScheduleForE(ScheduleVO schedule) {
		log.info("getMonthScheduleForE.............");

		return mapper.getMonthScheduleForE(schedule);
	}

	@Override
	public int insertSchedule(ScheduleVO schedule) {
		log.info("insertOnce.........................");

		return mapper.insertSchedule(schedule);
	}

	@Scheduled(cron = "0/3 * * * * *")
	public void testtask() {
		log.info("test");
	}

}
