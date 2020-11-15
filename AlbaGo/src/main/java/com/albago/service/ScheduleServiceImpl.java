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
	public ScheduleVO getTodaysSchedule(ScheduleVO schedule) {
		log.info("getTodaysSchedule................");

		return mapper.getTodaysSchedule(schedule);
	}

}
