package com.albago.mapper;

import java.util.List;

import com.albago.domain.ScheduleRepeatVO;

public interface ScheduleRepeatMapper {

	public int insertRepeat(ScheduleRepeatVO scheduleRepeat);

	public List<ScheduleRepeatVO> getListForCheckDuplicate(ScheduleRepeatVO scheduleRepeat);
}
