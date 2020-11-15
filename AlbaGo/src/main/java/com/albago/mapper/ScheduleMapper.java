package com.albago.mapper;

import java.util.List;

import com.albago.domain.ScheduleVO;

public interface ScheduleMapper {

	public ScheduleVO getTodaysScheduleForE(ScheduleVO schedule);

	public int arrive(int s_code);

	public int leave(int s_code);

	public List<ScheduleVO> getWeekScheduleForE(ScheduleVO schedule);
}