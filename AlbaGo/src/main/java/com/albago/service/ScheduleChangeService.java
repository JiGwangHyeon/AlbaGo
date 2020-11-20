package com.albago.service;

import com.albago.domain.ScheduleChangeVO;

public interface ScheduleChangeService {

	public boolean isWritable(int s_code);

	public int requestChangeSchedule(ScheduleChangeVO scheduleChange);

//	public int modifyRequest(ScheduleChangeVO scheduleChange);

	public ScheduleChangeVO getRequest(int s_code);

	public int cancelRequest(int s_code);
}
