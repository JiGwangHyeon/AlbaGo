package com.albago.service;

import com.albago.domain.ScheduleRepeatVO;

public interface ScheduleRepeatService {

	public int insertRepeat(ScheduleRepeatVO scheduleRepeat);

	public int checkDuplicateRepeat(ScheduleRepeatVO scheduleRepeat);
}
