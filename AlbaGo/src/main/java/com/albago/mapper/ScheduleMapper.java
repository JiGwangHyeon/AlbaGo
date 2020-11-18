package com.albago.mapper;

import java.util.List;

import com.albago.domain.ScheduleVO;

public interface ScheduleMapper {

	public ScheduleVO getTodaysScheduleForE(ScheduleVO schedule);

	public int arrive(ScheduleVO schedule);

	public int leave(ScheduleVO schedule);

	public List<ScheduleVO> getWeekScheduleForE(ScheduleVO schedule);

	public List<ScheduleVO> getMonthScheduleForE(ScheduleVO schedule);

	public int insertSchedule(ScheduleVO schedule);

	public List<ScheduleVO> getListTwoDaysAgo();

	public List<ScheduleVO> getListByCompany(int c_code);

	public int checkDuplicate(ScheduleVO schedule);
}