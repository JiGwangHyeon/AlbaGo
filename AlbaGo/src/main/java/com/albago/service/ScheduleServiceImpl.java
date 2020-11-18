package com.albago.service;

import java.util.List;

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
	public int arrive(ScheduleVO schedule) {
		log.info("arrive................");

		return mapper.arrive(schedule);
	}

	@Override
	public int leave(ScheduleVO schedule) {
		log.info("leave................");

		return mapper.leave(schedule);
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

		if (mapper.checkDuplicate(schedule) != 0) {
			return -1;
		}

		return mapper.insertSchedule(schedule);
	}

//	@Scheduled(cron = "0/3 * * * * *")
	public void testtask() {
		log.info("test");
	}

}
