package com.albago.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleVO;
import com.albago.mapper.ScheduleChangeMapper;
import com.albago.mapper.ScheduleMapper;
import com.albago.mapper.ScheduleRepeatMapper;
import com.albago.util.ForLog;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private ScheduleMapper scheduleMapper;
	private ScheduleRepeatMapper scheduleRepeatMapper;
	private ScheduleChangeMapper scheduleChangeMapper;

	// 오늘의 일정 가져오기
	@Override
	public ScheduleVO getTodaysSchedule(ScheduleVO schedule) {

		log.info("getTodaysSchedule" + ForLog.dot);
		log.info("c_code: " + schedule.getC_code());
		log.info("u_id: " + schedule.getU_id());

		return scheduleMapper.selectSingleByCcodeIdToday(schedule);
	}

	// 일정 하나 가져오기
	@Override
	public ScheduleVO getScheduleSingle(ScheduleVO schedule) {

		log.info("getSchedule" + ForLog.dot);
		log.info("schedule :" + schedule.toString());

		return scheduleMapper.selectSingleByScode(schedule);
	}

	// 출근
	@Override
	public int arrive(ScheduleVO schedule) {

		log.info("arrive" + ForLog.dot);
		log.info("schedule :" + schedule.toString());

		return scheduleMapper.updateArrive(schedule);
	}

	// 퇴근
	@Override
	public int leave(ScheduleVO schedule) {

		log.info("leave" + ForLog.dot);
		log.info("schedule :" + schedule.toString());

		return scheduleMapper.updateLeave(schedule);
	}

	// 주 별 일정 가져오기
	@Override
	public List<ScheduleVO> getScheduleWeek(ScheduleVO schedule, int week) {

		log.info("getWeekScheduleForE" + ForLog.dot);
		log.info("schedule: " + schedule.toString());
		log.info("week: " + week);

		Calendar s_start = Calendar.getInstance();

		if (s_start.get(Calendar.DAY_OF_WEEK) == 1) {
			s_start.add(Calendar.DATE, -6 + week * 7);
		} else {
			s_start.add(Calendar.DATE, -s_start.get(Calendar.DAY_OF_WEEK) + 2 + week * 7);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		schedule.setS_start(sdf.format(s_start.getTime()));

		return scheduleMapper.selectMultiByCcodeIdWeek(schedule);
	}

	// 월 별 일정 가져오기
	@Override
	public List<ScheduleVO> getScheduleMonth(ScheduleVO schedule, int year, int month) {

		log.info("getMonthScheduleForE" + ForLog.dot);
		log.info("schedule: " + schedule.toString());
		log.info("year :" + year);
		log.info("month: " + month);

		Calendar s_start = Calendar.getInstance();

		s_start.set(Calendar.YEAR, year);
		s_start.set(Calendar.MONTH, month - 1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		schedule.setS_start(sdf.format(s_start.getTime()));

		return scheduleMapper.selectMultiByCcodeIdMonth(schedule);
	}

	// 근무 신청하기
	@Override
	public int applySchedule(ScheduleVO schedule) {

		log.info("insertOnce" + ForLog.dot);
		log.info("schedule: " + schedule.toString());

		if (scheduleMapper.getCountDuplicated(schedule) != 0) {
			return -1;
		}

		return scheduleMapper.insert(schedule);
	}

//////////////////////////////////////////////////////////////
	// 근무 신청 내용 수정하기
	@Override
	public int editAppliedSchedule(ScheduleVO schedule) {

		log.info("editAppliedSchedule" + ForLog.dot);
		log.info("schedule :" + schedule.toString());

		return scheduleMapper.updateStartEnd(schedule);
	}

	// 근무 신청 취소하기
	@Override
	public int cancelAppliedSchedule(ScheduleVO schedule) {

		log.info("cancelSchedule" + ForLog.dot);
		log.info("schedule :" + schedule.toString());

		return scheduleMapper.deleteByScodeStatIsR(schedule);
	}

	// 거절된 스케줄 삭제하기
	/*
	 * @Override public int deleteRejectedSchedule(ScheduleVO schedule) {
	 * 
	 * log.info("deleteRejectedSchedule" + ForLog.dot); log.info("schedule :" +
	 * schedule.toString());
	 * 
	 * return scheduleMapper.deleteByCcodeStatIsR(schedule); }
	 */

	// 거절된 스케줄 목록 가져오기
//	@Override
//	public List<ScheduleVO> getRejectedSchedule(ScheduleVO schedule) {
//
//		log.info("getRejectedSchedule" + ForLog.dot);
//		log.info("schedule :" + schedule.toString());
//
//		return scheduleMapper.selectMultiByCcodeIdStatIsR(schedule);
//	}

//	@Scheduled(cron = "0/3 * * * * *")
	public void testtask() {
		log.info("test");
	}

}
