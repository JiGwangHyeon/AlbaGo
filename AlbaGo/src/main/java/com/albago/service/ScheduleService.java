package com.albago.service;

import com.albago.domain.ScheduleVO;

public interface ScheduleService {

	public ScheduleVO getTodaysScheduleForE(ScheduleVO schedule);

	public int arrive(int s_code);

	public int leave(int s_code);
}
