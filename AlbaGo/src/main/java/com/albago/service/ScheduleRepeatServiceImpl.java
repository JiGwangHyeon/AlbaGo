package com.albago.service;

import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.mapper.ScheduleRepeatMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class ScheduleRepeatServiceImpl implements ScheduleRepeatService {

	private ScheduleRepeatMapper mapper;

	@Override
	public int insertRepeat(ScheduleRepeatVO scheduleRepeat) {
		log.info("insertRepeat..............");

		return mapper.insertRepeat(scheduleRepeat);
	}

}
