package com.albago.service;

import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleChangeVO;
import com.albago.mapper.ScheduleChangeMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ScheduleChangeServiceImpl implements ScheduleChangeService {

	private ScheduleChangeMapper mapper;

	@Override
	public boolean isWritable(int s_code) {
		log.info("isWritable.................");

		if (mapper.checkExist(s_code) == 0)
			return true;

		return false;
	}

	@Override
	public int requestChangeSchedule(ScheduleChangeVO scheduleChange) {
		log.info("requestChangeSchedule..............");

		if (isWritable(scheduleChange.getS_code())) {
			return mapper.insert(scheduleChange);
		}

		return 0;
	}

	/*
	 * @Override public int modifyRequest(ScheduleChangeVO scheduleChange) {
	 * log.info("modifyRequest.......................");
	 * 
	 * return mapper.update(scheduleChange); }
	 */

	@Override
	public ScheduleChangeVO getRequest(int s_code) {
		log.info("getRequest............................");

		return mapper.select(s_code);
	}

	@Override
	public int cancelRequest(int s_code) {
		log.info("cancelRequest......................");

		return mapper.delete(s_code);
	}

}
