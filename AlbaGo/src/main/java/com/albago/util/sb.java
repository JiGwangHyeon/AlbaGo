package com.albago.util;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;

public class sb {

	// service
	// service
	// service
	// service
	// service

	// ***********************/관리자/근무 삭제 신청 조회/승인/거절**************************//

	public List<ScheduleVO> getListAppliedToCancelSchedule(long c_code);

	public int permitAppliedToCancelSchedule(int s_code);

	public int rejectAppliedToCancelSchedule(int s_code);

	// ***********************/관리자/근무 삭제 반복 신청 조회/승인/거절**************************//

	public List<ScheduleRepeatVO> getListAppliedToCancelRepeatedSchedule(long c_code);

	public int permitAppliedToCancelRepeatedSchedule(int sr_code);

	public int rejectAppliedToCancelRepeatedSchedule(int sr_code);

	// serviceImpl
	// serviceImpl
	// serviceImpl
	// serviceImpl
	// serviceImpl

	// ***********************/관리자/근무 취소 신청 조회/승인/거절**************************//

	@Override
	public List<ScheduleVO> getListAppliedToCancelSchedule(long c_code) { // 근무 취소 신청 목록 조회

		log.info("getListAppliedToCancelSchedule");
		log.info("c_code: " + c_code);

		return scheduleMapper.selectMultiByCcodeStatIsD(c_code); // select stat is D
	}

	@Override
	public int permitAppliedToCancelSchedule(int s_code) { // 근무 취소 신청 승인

		log.info("permitAppliedToCancelSchedule");
		log.info("s_code: " + s_code);

		// firebase push
		// 여기에 푸시 알림 넣어줘야함. "근무 취소가 승인되었습니다"

		return scheduleMapper.delete(s_code); // delete
	}

	@Override
	public int rejectAppliedToCancelSchedule(int s_code) { // 근무 취소 신청 거절

		log.info("rejectAppliedToCancelSchedule");
		log.info("s_code: " + s_code);

		// firebase push // 여기에 푸시 알림 넣어줘야함. "근무 취소가 거절되었습니다"

		return scheduleMapper.updateStatToP(schedule); // update stat to p
	}

	// ***********************/관리자/반복근무 취소 신청 조회/승인/거절**************************//

	@Override
	public List<ScheduleRepeatVO> getListAppliedToCancelRepeatedSchedule(long c_code) {

		log.info("getListAppliedToCancelRepeatedSchedule");
		log.info("c_code: " + c_code);

		return scheduleRepeatMapper.selectMultiByCcodeStatIsW(c_code); // select stat is D
	}

	@Override
	public int permitAppliedToCancelRepeatedSchedule(int sr_code) {

		log.info("permitListAppliedToCancelRepeatedSchedule");
		log.info("sr_code: " + sr_code);

		// firebase push
		// 여기에 푸시 알림 넣어줘야함. "반복근무 취소가 승인되었습니다"

		if (scheduleMapper.deleteBySrcodeStart(sr_code) != 0 && scheduleRepeatMapper.deleteBySrcode(sr_code) != 0)
			// delete from schedule_repeat where sr_code = #{sr_code}
			// delete from schedule where sr_code = #{sr_code} and s_start > sysdate
			return 1;

		return 0;
	}

	@Override
	public int rejectAppliedToCancelRepeatedSchedule(int sr_code) {

		log.info("rejectListAppliedToCancelRepeatedSchedule");
		log.info("sr_code: " + sr_code);

		// firebase push // 여기에 푸시 알림 넣어줘야함. "반복근무 취소가 거절되었습니다"

		return scheduleRepeatMapper.updateStatToP(sr_code); // update stat to p
	}

}
