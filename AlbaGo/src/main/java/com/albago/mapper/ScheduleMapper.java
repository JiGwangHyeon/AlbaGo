package com.albago.mapper;

import java.util.List;

import com.albago.domain.ScheduleVO;

public interface ScheduleMapper {

//	직원 - 기본 기능

	public ScheduleVO selectSingleByCcodeIdToday(ScheduleVO schedule); // 오늘에 해당하는 건 조회

	public ScheduleVO selectSingleByScode(ScheduleVO schedule); // 주어진 scode로 조회

	public int updateArrive(ScheduleVO schedule); // arrive 수정

	public int updateLeave(ScheduleVO schedule); // leave 수정

	public List<ScheduleVO> selectMultiByCcodeIdWeek(ScheduleVO schedule); // 주 별 목록 조회

	public List<ScheduleVO> selectMultiByCcodeIdMonth(ScheduleVO schedule); // 월 별 목록 조회

//	직원 - 근무 신청

	public int getCountDuplicated(ScheduleVO schedule); // 근무 신청 전 겹치는 일정 확인

	public int insert(ScheduleVO schedule); // 근무 신청

	public int updateStartEnd(ScheduleVO schedule); // 근무 신청 내역 수정

	public int deleteByScodeStatIsW(ScheduleVO schedule); // 한 건 삭제

//	직원 - 근무 취소 신청

	public int updateStatToD(ScheduleVO schedule); // 스탯을 D로 변경 - 근무 취소 신청

	public int updateStatToP(ScheduleVO schedule); // 스탯을 P로 변경 - 근무 취소 신청 취소

//	직원 - 반복근무

	public List<ScheduleVO> selectMultiByCcodeIdStart(ScheduleVO schedule); // 반복근무 삽입 시 기존의 schedule과 겹칠지 확인하기 위해 리스트
																			// 요청

//	관리자 - 근무 신청

	public List<ScheduleVO> selectMultiByCcodeStatIsW(long c_code);// stat이 w인 schedule 조회 - 근무 신청 조회

	public int delete(int s_code);// schedule 삭제 - 근무 신청 거절

//	public int updateStatToP(int s_code); // 근무 신청 승인

//	관리자 - 근무 취소 신청

	public List<ScheduleVO> selectMultiByCcodeStatIsD(long c_code);

//	public int delete(int s_code); // 근무 취소 승인

//	public int updateStatToP(int s_code); // 근무 취소 거절

//	관리자 - 반복근무 취소 신청

	public int deleteBySrcodeStart(int sr_code); // 해당 sr_code를 가진 schedule에 대해 승인 이후의 건 삭제 - 반복근무 취소 승인

//	급여 관련

	public List<ScheduleVO> selectMultiByStartIsTwoDayAgo(); // 이틀 전 근무 목록 조회 - salary 변환 시 사용

	public List<ScheduleVO> selectMultiForExpect(ScheduleVO schedule); // 예상 급여 계산 시 사용
}