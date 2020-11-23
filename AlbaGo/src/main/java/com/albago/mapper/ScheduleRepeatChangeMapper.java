package com.albago.mapper;

import com.albago.domain.ScheduleRepeatChangeVO;

public interface ScheduleRepeatChangeMapper {

	public int getCountBySrcodeStatIsW(int sr_code); // 중복 개수 조회

	public int insert(ScheduleRepeatChangeVO scheduleRepeatChange); // 반복근무 변경 신청

	public ScheduleRepeatChangeVO selectSingleBySrcodeStatIsW(int sr_code); // 반복근무 변경 신청 내역 조회

	public int updateStartEndRepeatReason(ScheduleRepeatChangeVO scheduleRepeatChange); // 반복근무 변경 신청 내역 수정

	public int deleteBySrcodeStatIsW(int sr_code); // 반복근무 변경 취소

//	public int update(ScheduleChangeVO scheduleChange);

//	public int updateStat(ScheduleChangeVO scheduleChange);

}
