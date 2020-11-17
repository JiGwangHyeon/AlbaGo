package com.albago.service;

import java.util.List;

import com.albago.domain.ScheduleVO;

public interface ScheduleService {

	public ScheduleVO getTodaysScheduleForE(ScheduleVO schedule);

	public int arrive(ScheduleVO schedule);

	public int leave(ScheduleVO schedule);

	public List<ScheduleVO> getWeekScheduleForE(ScheduleVO schedule);

	public List<ScheduleVO> getMonthScheduleForE(ScheduleVO schedule);

	public int insertSchedule(ScheduleVO schedule);
}
