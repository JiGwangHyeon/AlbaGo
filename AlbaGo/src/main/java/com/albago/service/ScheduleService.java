package com.albago.service;

import java.util.List;

import com.albago.domain.ScheduleChangeVO;
import com.albago.domain.ScheduleRepeatChangeVO;
import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;

public interface ScheduleService {

	// ***********************Schedule 기본 기능**************************//

	public ScheduleVO getTodaysSchedule(ScheduleVO schedule);

	public ScheduleVO getScheduleSingle(ScheduleVO schedule);

	public int arrive(ScheduleVO schedule);

	public int leave(ScheduleVO schedule);

	public List<ScheduleVO> getScheduleWeek(ScheduleVO schedule, int week);

	public List<ScheduleVO> getScheduleMonth(ScheduleVO schedule, int year, int month);

//	public List<ScheduleRepeatVO> lookUpListRepeatedSchedule(ScheduleRepeatVO scheduleRepeat); // 반복 일정 조회(신청 중, 신청 완료, 변경 신청 중)

	// ***********************/직원/근무 일정 신청 및 조회**************************//

	public int applySchedule(ScheduleVO schedule); // 근무 신청

	public int editAppliedSchedule(ScheduleVO schedule); // 근무 신청 내용 수정

	public int cancelAppliedSchedule(ScheduleVO schedule); // 근무 신청 취소

	// ***********************/직원/근무 일정 반복 신청 및 조회**************************//

	public int applyRepeatedSchedule(ScheduleRepeatVO scheduleRepeat); // 신청

	public ScheduleRepeatVO lookUpAppliedRepeatedSchedule(ScheduleRepeatVO scheduleRepeat); // 조회

	public int editAppliedRepeatedSchedule(ScheduleRepeatVO scheduleRepeat); // 수정

	public int cancelAppliedRepeatedSchedule(ScheduleRepeatVO scheduleRepeat); // 취소

	// ***********************/직원/근무 변경 신청**************************//

	public int applyScheduleChange(ScheduleChangeVO scheduleChange);

	public int editScheduleChange(ScheduleChangeVO scheduleChange);

	public ScheduleChangeVO lookUpAppliedScheduleChange(int s_code);

	public int cancelScheduleChange(int s_code);

	// ***********************/직원/근무 일정 반복 변경 신청**************************//

	public int applyRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange, String u_id, long c_code);

	public int editRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange, String u_id, long c_code);

	public ScheduleRepeatChangeVO lookupRepeatedScheduleChange(int sr_code);

	public int cancelRepeatedScheduleChange(int sr_code);

	// ***********************/직원/근무 삭제 신청**************************//

	public int applyToCancelSchedule(ScheduleVO schedule);

	public int cancelToCancelSchedule(ScheduleVO schedule);

	// ***********************/직원/반복근무 삭제 신청**************************//

	public int applyToCancelRepeatedSchedule(ScheduleRepeatVO scheduleRepeat);

	public int cancelToCancelRepeatedSchedule(ScheduleRepeatVO scheduleRepeat);

	// ***********************/관리자/근무 일정 신청 조회/승인/거절**************************//

	public List<ScheduleVO> getListAppliedSchedule(long c_code); // 상태가 신청중 인 신청 목록 조회

	public int rejectAppliedSchedule(int s_code); // 근무 신청 거절

//	public int permitAppliedSchedule(int s_code); // 근무 신청 승인

	// ***********************/관리자/반복근무 일정 신청 조회/승인/거절**************************//

	public List<ScheduleRepeatVO> getListAppliedRepeatedSchedule(long c_code); // 목록 조회

	public int rejectAppliedRepeatedSchedule(int sr_code); // 신청 거절

	public int permitAppliedRepeatedSchedule(int sr_code); // 신청 승인

	// ***********************/관리자/근무 일정 변경 조회/승인/거절**************************//

//	public void getListAppliedScheduleChange(ScheduleChangeVO scheduleChange); // 상태가 신청중 인 신청 목록 조회

//	public void rejectAppliedScheduleChange(ScheduleChangeVO scheduleChange); // 근무 신청 거절

//	public void permitAppliedScheduleChange(ScheduleChangeVO scheduleChange); // 근무 신청 승인

	// ***********************/관리자/반복근무 일정 신청 조회/승인/거절**************************//

//	public void getListAppliedRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange); // 목록 조회

//	public void rejectAppliedRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange); // 신청 거절

//	public void permitAppliedRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange); // 신청 승인

	// ***********************/관리자/근무 삭제 신청 조회/승인/거절**************************//

	public List<ScheduleVO> getListAppliedToCancelSchedule(long c_code);

	public int permitAppliedToCancelSchedule(int s_code);

//	public int rejectAppliedToCancelSchedule(int s_code);

	// ***********************/관리자/근무 삭제 반복 신청 조회/승인/거절**************************//

	public List<ScheduleRepeatVO> getListAppliedToCancelRepeatedSchedule(long c_code);

	public int permitAppliedToCancelRepeatedSchedule(int sr_code);

//	public int rejectAppliedToCancelRepeatedSchedule(int sr_code);
}
