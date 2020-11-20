package com.albago.mapper;

import com.albago.domain.ScheduleChangeVO;

// 스케줄 변경 : 앱(사용자)에서는 신청, 수정, 삭제, 조회(한건)만 가능하면 될 듯, 웹에서는 조회(여러건), 승인, 거절 구현해야함
// 하나의 s_code에 대해서는 하나의 신청중인 상태의 sa_code를 가질 수 있다.
// 변경 폼 작성 버튼 : 해당 s_code에 대해 신청중인 내용이 없는지 확인, 있다면 신청 폼 작성 버튼 비활성화
// 시간 변경만 가능하므로 다른 사업장의 시간과 겹치는지 확인

public interface ScheduleChangeMapper {

	public int checkExist(int s_code);

	public int insert(ScheduleChangeVO scheduleChange);

//	public int update(ScheduleChangeVO scheduleChange);

	public int delete(int s_code);

	public ScheduleChangeVO select(int s_code);

//	public int updateStat(ScheduleChangeVO scheduleChange);
}
