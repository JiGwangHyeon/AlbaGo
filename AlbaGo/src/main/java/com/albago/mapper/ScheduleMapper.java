package com.albago.mapper;

import java.util.List;

import com.albago.domain.ScheduleVO;

public interface ScheduleMapper {

	public int insert(ScheduleVO schedule); // 근무 신청

	public int updateStartEnd(ScheduleVO schedule); // 근무 신청 내역 수정

	public int updateArrive(ScheduleVO schedule); // arrive 수정

	public int updateLeave(ScheduleVO schedule); // leave 수정

	public int updateStatToD(ScheduleVO schedule); // 스탯을 D로 변경

	public int deleteByScodeStatIsR(ScheduleVO schedule); // 한 건 삭제

//	public int deleteByCcodeStatIsR(ScheduleVO schedule); // 스탯이 R인 건 삭제

	public int getCountDuplicated(ScheduleVO schedule); // 근무 신청 전 겹치는 일정 확인

	public ScheduleVO selectSingleByScode(ScheduleVO schedule); // 주어진 scode로 조회

	public ScheduleVO selectSingleByCcodeIdToday(ScheduleVO schedule); // 오늘에 해당하는 건 조회

	public List<ScheduleVO> selectMultiByStartIsTwoDayAgo(); // 이틀 전 근무 목록 조회

//	public List<ScheduleVO> selectMultiByCcodeIdStatIsR(ScheduleVO schedule); // 스탯이 R인 건 조회

	public List<ScheduleVO> selectMultiByCcodeIdWeek(ScheduleVO schedule); // 주 별 목록 조회

	public List<ScheduleVO> selectMultiByCcodeIdMonth(ScheduleVO schedule); // 월 별 목록 조회

	public List<ScheduleVO> selectMultiByCcodeIdStart(ScheduleVO schedule); // 근무 반복 삽입 시 기존의 schedule과 겹칠지 확인하기 위해 리스트
																			// 요청

	public List<ScheduleVO> selectMultiForExpect(ScheduleVO schedule);
}