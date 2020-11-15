package com.albago.service;

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

}
