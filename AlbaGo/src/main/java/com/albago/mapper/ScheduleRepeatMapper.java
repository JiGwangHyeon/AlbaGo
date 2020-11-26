package com.albago.mapper;

import java.util.List;

import com.albago.domain.ScheduleRepeatVO;

public interface ScheduleRepeatMapper {

//	직원 - 반복근무 신청

	public List<ScheduleRepeatVO> getCountDuplicated(ScheduleRepeatVO scheduleRepeat); // 겹치는 일정 체크

	public int insert(ScheduleRepeatVO scheduleRepeat); // 삽입

	public ScheduleRepeatVO selectSingleBySrcode(ScheduleRepeatVO scheduleRepeat); // 한 건 조회

	public int updateStartEndRepeat(ScheduleRepeatVO scheduleRepeat); // 반복근무 신청 내용 수정

	public int deleteBySrcodeStatIsW(ScheduleRepeatVO scheduleRepeat); // 반복근무 신청 취소

//	직원 - 반복근무 취소 신청

	public int updateStatToD(ScheduleRepeatVO scheduleRepeat); // 스탯을 D로 변경 - 삭제 요청

	public int updateStatToP(ScheduleRepeatVO scheduleRepeat); // 스탯을 P로 변경 - 삭제 요청 취소

//	관리자 - 반복근무 신청

	public List<ScheduleRepeatVO> selectMultiByCcode(ScheduleRepeatVO scheduleRepeat); // c_code와 stat이 'W'인
																						// schedule_repeat 목록
																						// 가져오기
	// - 반복근무 신청 목록

	public int delete(int sr_code); // 해당 sr_code 삭제 - 반복근무 신청 거절

	public int updateStatToPAndReturn(int sr_code); // stat을 p로 변경 - 반복근무 신청 승인

	public ScheduleRepeatVO select(int sr_code);

//	관리자 - 반복근무 취소 신청

	public List<ScheduleRepeatVO> selectMultiByCcodeStatIsD(long c_code); // c_code와 stat이 'D'인 schedule_repeat 목록 가져오기
																			// - 반복근무 취소 신청 목록

	public int deleteBySrcode(int sr_code); // 해당 sr_code를 가진 schedule_repeat 삭제 - 반복근무 취소 승인

//	public int updateStatToP(int sr_code); // sr_code의 상태를 p로 변경 - 반복근무 취소 거절

}
