package com.albago.mapper;

import java.util.List;

import com.albago.domain.ScheduleRepeatVO;

public interface ScheduleRepeatMapper {

	public int insert(ScheduleRepeatVO scheduleRepeat);

	public List<ScheduleRepeatVO> getCountDuplicated(ScheduleRepeatVO scheduleRepeat);
}
