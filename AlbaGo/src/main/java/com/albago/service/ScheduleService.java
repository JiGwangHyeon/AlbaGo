package com.albago.service;

import java.util.List;

import com.albago.domain.ScheduleChangeVO;
import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;

public interface ScheduleService {

	// ***********************근무 일정 신청 및 조회**************************//

	public ScheduleVO getTodaysSchedule(ScheduleVO schedule);

	public ScheduleVO getScheduleSingle(ScheduleVO schedule);

	public int arrive(ScheduleVO schedule);

	public int leave(ScheduleVO schedule);

	public List<ScheduleVO> getScheduleWeek(ScheduleVO schedule, int week);

	public List<ScheduleVO> getScheduleMonth(ScheduleVO schedule, int year, int month);

	public int applySchedule(ScheduleVO schedule); // 근무 신청

	public int editAppliedSchedule(ScheduleVO schedule); // 근무 신청 내용 수정

	public int cancelAppliedSchedule(ScheduleVO schedule); // 근무 신청 취소

//	public int deleteRejectedSchedule(ScheduleVO schedule); // 거절된 schedule 데이터 삭제

//	public List<ScheduleVO> getRejectedSchedule(ScheduleVO schedule); // 거절된 schedule 목록 불러오기

	// ***********************근무 일정 반복 신청 및 조회**************************//

	public int applyRepeatedSchedule(ScheduleRepeatVO scheduleRepeat);

	// ***********************근무 변경 신청**************************//

	public boolean isWritable(int s_code);

	public int applyChangeSchedule(ScheduleChangeVO scheduleChange);

//	public int modifyRequest(ScheduleChangeVO scheduleChange);

	public ScheduleChangeVO getApplySingle(int s_code);

	public int cancelRequest(int s_code);

	// ***********************근무 일정 반복 변경 신청**************************//

}
