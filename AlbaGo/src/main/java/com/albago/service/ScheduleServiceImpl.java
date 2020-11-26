package com.albago.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleChangeVO;
import com.albago.domain.ScheduleRepeatChangeVO;
import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;
import com.albago.mapper.ScheduleChangeMapper;
import com.albago.mapper.ScheduleMapper;
import com.albago.mapper.ScheduleRepeatChangeMapper;
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
	private ScheduleRepeatChangeMapper scheduleRepeatChangeMapper;

	// ***********************Schedule 기본 기능**************************//

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

	// ***********************/직원/근무 일정 신청 및 조회**************************//

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

		return scheduleMapper.deleteByScodeStatIsW(schedule);
	}

	// ***********************/직원/반복근무 신청 및 조회**************************//

	@Override
	public int applyRepeatedSchedule(ScheduleRepeatVO scheduleRepeat) {
		log.info("applyRepeatedSchedule" + ForLog.dot);

		int dupCheck = checkDuplicateRepeat(scheduleRepeat);

		if (dupCheck == 1) {
			log.info("Duplicated Schedule_Repeat Exist" + ForLog.dot);
			return 0;
		} else if (dupCheck == -1) {
			log.info("Duplicated Schedule Exist" + ForLog.dot);
			return -1;
		}

		int result = scheduleRepeatMapper.insert(scheduleRepeat);

		log.info("applyRepeatedSchedule_result: " + result);

		/*
		 * 관리자가 실행해야 할 부분 if (result == 1) { whenInsert(scheduleRepeat); }
		 */
		return result;
	}

	@Override
	public List<ScheduleRepeatVO> getListAppliedRepeatedSchedule(ScheduleRepeatVO scheduleRepeat) { // 목록 조회

		log.info("getListAppliedRepeatSchedule");
		log.info("scheduleRepeat: " + scheduleRepeat.toString());

		return scheduleRepeatMapper.selectMultiByCcode(scheduleRepeat);
	}

	@Override
	public ScheduleRepeatVO lookUpAppliedRepeatedSchedule(ScheduleRepeatVO scheduleRepeat) {

		log.info("lookUpAppliedRepeatedSchedule" + ForLog.dot);
		log.info("scheduleRepeat: " + scheduleRepeat.toString());

		return scheduleRepeatMapper.selectSingleBySrcode(scheduleRepeat);
	}

	@Override
	public int editAppliedRepeatedSchedule(ScheduleRepeatVO scheduleRepeat) {

		log.info("editAppliedRepeateSchedule" + ForLog.dot);
		log.info("scheduleRepeat: " + scheduleRepeat.toString());

		scheduleRepeat = scheduleRepeatMapper.selectSingleBySrcode(scheduleRepeat);

		int dupCheck = checkDuplicateRepeat(scheduleRepeat);

		if (dupCheck == 1) {
			log.info("Duplicated Schedule_Repeat Exist" + ForLog.dot);
			return 0;
		} else if (dupCheck == -1) {
			log.info("Duplicated Schedule Exist" + ForLog.dot);
			return -1;
		}

		return scheduleRepeatMapper.updateStartEndRepeat(scheduleRepeat);
	}

	@Override
	public int cancelAppliedRepeatedSchedule(ScheduleRepeatVO scheduleRepeat) {

		log.info("cancelAppliedRepeateSchedule" + ForLog.dot);
		log.info("scheduleRepeat: " + scheduleRepeat.toString());

		return scheduleRepeatMapper.deleteBySrcodeStatIsW(scheduleRepeat);
	}

	// ***********************/직원/근무 변경 신청**************************//

	@Override
	public int applyScheduleChange(ScheduleChangeVO scheduleChange) {

		log.info("applyScheduleChange" + ForLog.dot);
		log.info("scheduleChange: " + scheduleChange.toString());

		if (scheduleChangeMapper.getCountByScodeStatIsW(scheduleChange.getS_code()) == 0) {
			return scheduleChangeMapper.insert(scheduleChange);
		}

		return 0;
	}

	@Override
	public int editScheduleChange(ScheduleChangeVO scheduleChange) {

		log.info("editScheduleChange" + ForLog.dot);
		log.info("scheduleChange: " + scheduleChange.toString());

		return scheduleChangeMapper.updateStartEndReason(scheduleChange);
	}

	@Override
	public ScheduleChangeVO lookUpAppliedScheduleChange(int s_code) {

		log.info("lookUpAppliedScheduleChange" + ForLog.dot);
		log.info("s_code: " + s_code);

		return scheduleChangeMapper.selectSingleByScodeStatIsW(s_code);
	}

	@Override
	public int cancelScheduleChange(int s_code) {

		log.info("cancelScheduleChange" + ForLog.dot);
		log.info("s_code: " + s_code);

		return scheduleChangeMapper.deleteByScodeStatIsW(s_code);
	}

	// ***********************/직원/근무 일정 반복 변경 신청**************************//

	@Override
	public int applyRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange, String u_id, long c_code) {

		log.info("applyRepeatedScheduleChange" + ForLog.dot);
		log.info("scheduleRepeatChange: " + scheduleRepeatChange.toString());
		log.info("u_id: " + u_id);
		log.info("c_code: " + c_code);

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setU_id(u_id);
		scheduleRepeat.setC_code(c_code);
		scheduleRepeat.setSr_start(scheduleRepeatChange.getSrc_start());
		scheduleRepeat.setSr_end(scheduleRepeatChange.getSrc_end());
		scheduleRepeat.setSr_repeat(scheduleRepeatChange.getSrc_repeat());

		int dupCheck = checkDuplicateRepeat(scheduleRepeat);

		if (dupCheck == 1) {
			log.info("Duplicated Schedule_Repeat Exist" + ForLog.dot);
			return 0;
		} else if (dupCheck == -1) {
			log.info("Duplicated Schedule Exist" + ForLog.dot);
			return -1;
		}

		return scheduleRepeatChangeMapper.insert(scheduleRepeatChange);
	}

	@Override
	public int editRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange, String u_id, long c_code) {

		log.info("editRepeatedScheduleChange" + ForLog.dot);
		log.info("scheduleRepeatChange: " + scheduleRepeatChange.toString());
		log.info("u_id: " + u_id);
		log.info("c_code: " + c_code);

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setU_id(u_id);
		scheduleRepeat.setC_code(c_code);
		scheduleRepeat.setSr_start(scheduleRepeatChange.getSrc_start());
		scheduleRepeat.setSr_end(scheduleRepeatChange.getSrc_end());
		scheduleRepeat.setSr_repeat(scheduleRepeatChange.getSrc_repeat());

		int dupCheck = checkDuplicateRepeat(scheduleRepeat);

		if (dupCheck == 1) {
			log.info("Duplicated Schedule_Repeat Exist" + ForLog.dot);
			return 0;
		} else if (dupCheck == -1) {
			log.info("Duplicated Schedule Exist" + ForLog.dot);
			return -1;
		}

		return scheduleRepeatChangeMapper.updateStartEndRepeatReason(scheduleRepeatChange);
	}

	@Override
	public ScheduleRepeatChangeVO lookupRepeatedScheduleChange(int sr_code) {

		log.info("lookupRepeatedScheduleChange" + ForLog.dot);
		log.info("sr_code: " + sr_code);

		return scheduleRepeatChangeMapper.selectSingleBySrcodeStatIsW(sr_code);
	}

	@Override
	public int cancelRepeatedScheduleChange(int sr_code) {

		log.info("cancelRepeatedScheduleChange" + ForLog.dot);
		log.info("sr_code: " + sr_code);

		return scheduleRepeatChangeMapper.deleteBySrcodeStatIsW(sr_code);
	}

	// ***********************/직원/근무 삭제 신청**************************//

	@Override
	public int applyToCancelSchedule(ScheduleVO schedule) {

		log.info("applyToCancelSchedule" + ForLog.dot);
		log.info("schedule: " + schedule.toString());

		return scheduleMapper.updateStatToD(schedule);
	}

	@Override
	public int cancelToCancelSchedule(ScheduleVO schedule) {

		log.info("cancelToCancelSchedule" + ForLog.dot);
		log.info("schedule: " + schedule.toString());

		return scheduleMapper.updateStatToP(schedule);
	}

	// ***********************/직원/반복근무 삭제 신청**************************//

	@Override
	public int applyToCancelRepeatedSchedule(ScheduleRepeatVO scheduleRepeat) {

		log.info("applyToCancelRepeatedSchedule" + ForLog.dot);
		log.info("scheduleRepeat: " + scheduleRepeat.toString());

		return scheduleRepeatMapper.updateStatToD(scheduleRepeat);
	}

	@Override
	public int cancelToCancelRepeatedSchedule(ScheduleRepeatVO scheduleRepeat) {

		log.info("cancelToCancelRepeatedSchedule" + ForLog.dot);
		log.info("scheduleRepeat: " + scheduleRepeat.toString());

		return scheduleRepeatMapper.updateStatToP(scheduleRepeat);
	}

	// ***********************/관리자/근무 일정 신청 조회/승인/거절**************************//

	public List<ScheduleVO> getListAppliedSchedule(long c_code) { // 상태가 신청중 인 신청 목록 조회

		log.info("getListAppliedSchedule");
		log.info("c_code: " + c_code);

		return scheduleMapper.selectMultiByCcodeStatIsW(c_code);
	}

	public int rejectAppliedSchedule(int s_code) { // 근무 신청 거절

		log.info("rejectAppliedSchedule");
		log.info("s_code: " + s_code);

		// firebase push
		// 여기에 푸시 알림 넣어줘야함. "근무 신청이 거절되었습니다"

		return scheduleMapper.delete(s_code);
	}

	/*
	 * public int permitAppliedSchedule(int s_code) { // 근무 신청 승인
	 * 
	 * log.info("permitAppliedSchedule"); log.info("s_code: " + s_code); // firebase
	 * push // 여기에 푸시 알림 넣어줘야함. "근무 신청이 승인되었습니다"
	 * 
	 * return scheduleMapper.updateStatToP(s_code); }
	 */

	// ***********************/관리자/반복근무 일정 신청 조회/승인/거절**************************//

	/*
	 * @Override public List<ScheduleRepeatVO> getListAppliedRepeatedSchedule(long
	 * c_code) { // 목록 조회
	 * 
	 * log.info("getListAppliedRepeatSchedule"); log.info("c_code: " + c_code);
	 * 
	 * return scheduleRepeatMapper.selectMultiByCcodeStatIsW(c_code); }
	 */

	@Override
	public int rejectAppliedRepeatedSchedule(int sr_code) {

		log.info("rejectAppliedRepeatedSchedule");
		log.info("sr_code: " + sr_code);

		// firebase push
		// 여기에 푸시 알림 넣어줘야함. "반복근무 신청이 거절되었습니다"

		return scheduleRepeatMapper.delete(sr_code);
	}

	@Override
	public int permitAppliedRepeatedSchedule(int sr_code) {

		log.info("permitAppliedRepeatedSchedule");
		log.info("sr_code: " + sr_code);

		// firebase push
		// 여기에 푸시 알림 넣어줘야함. "반복근무 신청이 승인되었습니다"

		int result = scheduleRepeatMapper.updateStatToPAndReturn(sr_code);

		if (result == 1) {
			ScheduleRepeatVO scheduleRepeat = scheduleRepeatMapper.select(sr_code);
			whenInsert(scheduleRepeat);
		}

		return result;
	}

	// ***********************/관리자/근무 일정 변경 조회/승인/거절**************************//

//	public void getListAppliedScheduleChange(ScheduleChangeVO scheduleChange); // 상태가 신청중 인 신청 목록 조회

//	public void rejectAppliedScheduleChange(ScheduleChangeVO scheduleChange); // 근무 신청 거절

//	public void permitAppliedScheduleChange(ScheduleChangeVO scheduleChange); // 근무 신청 승인

	// ***********************/관리자/반복근무 일정 신청 조회/승인/거절**************************//

//	public void getListAppliedRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange); // 목록 조회

//	public void rejectAppliedRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange); // 신청 거절

//	public void permitAppliedRepeatedScheduleChange(ScheduleRepeatChangeVO scheduleRepeatChange); // 신청 승인

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

	/*
	 * @Override public int rejectAppliedToCancelSchedule(int s_code) { // 근무 취소 신청
	 * 거절
	 * 
	 * log.info("rejectAppliedToCancelSchedule"); log.info("s_code: " + s_code);
	 * 
	 * // firebase push // 여기에 푸시 알림 넣어줘야함. "근무 취소가 거절되었습니다"
	 * 
	 * return scheduleMapper.updateStatToP(schedule); // update stat to p }
	 */

	// ***********************/관리자/반복근무 취소 신청 조회/승인/거절**************************//

	/*
	 * @Override public List<ScheduleRepeatVO>
	 * getListAppliedToCancelRepeatedSchedule(long c_code) {
	 * 
	 * log.info("getListAppliedToCancelRepeatedSchedule"); log.info("c_code: " +
	 * c_code);
	 * 
	 * return scheduleRepeatMapper.selectMultiByCcodeStatIsW(c_code); // select stat
	 * is D }
	 */

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

	/*
	 * @Override public int rejectAppliedToCancelRepeatedSchedule(int sr_code) {
	 * 
	 * log.info("rejectListAppliedToCancelRepeatedSchedule"); log.info("sr_code: " +
	 * sr_code);
	 * 
	 * // firebase push // 여기에 푸시 알림 넣어줘야함. "반복근무 취소가 거절되었습니다"
	 * 
	 * return scheduleRepeatMapper.updateStatToP(sr_code); // update stat to p }
	 */

	public int checkDuplicateRepeat(ScheduleRepeatVO scheduleRepeat) {
		log.info("checkDuplicateRepeat........................");

		String[] rDayList = scheduleRepeat.getSr_repeat().split("");
		String srStart = scheduleRepeat.getSr_start();
		String srEnd = scheduleRepeat.getSr_end();

		Calendar sst = Calendar.getInstance();
		Calendar set = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		try {
			sst.setTime(sdf.parse(srStart));
			set.setTime(sdf.parse(srEnd));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (sst.after(set))
			set.add(Calendar.DATE, 1);
		long millis = set.getTimeInMillis() - sst.getTimeInMillis();

		boolean dup = false;

		for (String i : rDayList) {
			ScheduleVO schedule = new ScheduleVO();
			schedule.setC_code(scheduleRepeat.getC_code());
			schedule.setU_id(scheduleRepeat.getU_id());
			schedule.setSr_code(Integer.parseInt(i));
			List<ScheduleVO> list = scheduleMapper.selectMultiByCcodeIdStart(schedule);
			for (ScheduleVO l : list) {
				Calendar sse = Calendar.getInstance();
				Calendar see = Calendar.getInstance();

				try {
					sse.setTime(sdf2.parse(l.getS_start()));
					see.setTime(sdf2.parse(l.getS_end()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Calendar ss = (Calendar) sse.clone();
				ss.set(Calendar.HOUR_OF_DAY, sst.get(Calendar.HOUR_OF_DAY));
				ss.set(Calendar.MINUTE, sst.get(Calendar.MINUTE));
				ss.set(Calendar.SECOND, sst.get(Calendar.SECOND));

				Calendar se = (Calendar) ss.clone();
				se.add(Calendar.MILLISECOND, (int) millis);

				if (see.after(ss) && (ss.after(sse) || ss.equals(sse))) {
					log.trace(sse.getTime() + "(sse) <=" + ss.getTime() + "(ss) <" + see.getTime() + "(see)");
					log.trace("s_code: " + l.getS_code());
					dup = true;
					break;
				}
				if ((see.after(se) || see.equals(se)) && se.after(sse)) {
					log.trace(sse.getTime() + "(sse) <" + se.getTime() + "(se) <=" + see.getTime() + "(see)");
					log.trace("s_code: " + l.getS_code());
					dup = true;
					break;

				}
				if ((se.after(see) || se.equals(see)) && (sse.after(ss) || sse.equals(ss))) {
					log.trace(ss.getTime() + "(ss) <=" + sse.getTime() + "(sse) and " + see.getTime() + "(see) <="
							+ se.getTime() + "(se)");
					log.trace("s_code: " + l.getS_code());
					dup = true;
					break;
				}
			}
			if (dup) {
				break;
			}
		}
		if (dup) {
			log.info("Duplicated Schedule Exist" + ForLog.dot);
			return -1;
		}

		List<ScheduleRepeatVO> scheduleRepeatExist = scheduleRepeatMapper.getCountDuplicated(scheduleRepeat);

		for (ScheduleRepeatVO exist : scheduleRepeatExist) {

			String[] rDayListEx = exist.getSr_repeat().split("");

			String srStartEx = exist.getSr_start();
			String srEndEx = exist.getSr_end();

			if (checkDuplicateDay(rDayList, rDayListEx) && checkTime(srStart, srStartEx, srEnd, srEndEx)) {

				log.info("Duplicated Schedule_Repeat Exist" + ForLog.dot);

				return 1;
			}
		}

		log.info("There's No Duplicated.");

		return 0;
	}

	public boolean checkDuplicateDay(String[] rDayList, String[] rDayListEx) {

		log.info("checkDuplicateDay Start" + ForLog.dot);

		HashSet<String> rDaySet = new HashSet<>();
		HashSet<String> rDaySetEx = new HashSet<>();

		rDaySet.addAll(Arrays.asList(rDayList));
		rDaySetEx.addAll(Arrays.asList(rDayListEx));

		if (!rDaySet.isEmpty()) {
			log.info("checkDuplicateDay End/return true" + ForLog.dot);
			return true;
		}

		log.info("checkDuplicateDay End/return false" + ForLog.dot);
		return false;
	}

	public boolean checkTime(String srStart, String srStartEx, String srEnd, String srEndEx) {

		log.info("checkTime Start" + ForLog.dot);

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Calendar ss = Calendar.getInstance();
		Calendar sse = Calendar.getInstance();
		Calendar se = Calendar.getInstance();
		Calendar see = Calendar.getInstance();

		try {
			ss.setTime(sdf.parse(srStart));
			sse.setTime(sdf.parse(sdf.format(sdf2.parse(srStartEx))));
			se.setTime(sdf.parse(srEnd));
			see.setTime(sdf.parse(sdf.format(sdf2.parse(srEndEx))));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		log.trace("ss: " + ss.getTime());
		log.trace("sse: " + sse.getTime());
		log.trace("se: " + se.getTime());
		log.trace("see: " + see.getTime());

		if (ss.after(se)) {
			se.add(Calendar.DATE, 1);
			log.trace("se_added: " + se.getTime());
		}

		if (sse.after(see)) {
			see.add(Calendar.DATE, 1);
			log.trace("see_added: " + see.getTime());
		}

		if (see.after(ss) && (ss.after(sse) || ss.equals(sse))) {
			log.trace(sse.getTime() + "(sse) <=" + ss.getTime() + "(ss) <" + see.getTime() + "(see)");
			log.info("checkTime End/return true......................");
			return true;
		}
		if ((see.after(se) || see.equals(se)) && se.after(sse)) {
			log.trace(sse.getTime() + "(sse) <" + se.getTime() + "(se) <=" + see.getTime() + "(see)");
			log.info("checkTime End/return true......................");
			return true;
		}
		if ((se.after(see) || se.equals(see)) && (sse.after(ss) || sse.equals(ss))) {
			log.trace(ss.getTime() + "(ss) <=" + sse.getTime() + "(sse) and " + see.getTime() + "(see) <="
					+ se.getTime() + "(se)");
			log.info("checkTime End/return true......................");
			return true;
		}

		log.info("checkTime End/return false......................");
		return false;
	}

	public int whenInsert(ScheduleRepeatVO scheduleRepeat) {

		log.info("whenInsert" + ForLog.dot);
		log.info("ScheduleRepeat: " + scheduleRepeat.toString());

		// 넘어온 VO가 가진 정보 지역 변수로 저장
		int sr_code = scheduleRepeat.getSr_code();
		long c_code = scheduleRepeat.getC_code();
		String u_id = scheduleRepeat.getU_id();
		String sr_start = scheduleRepeat.getSr_start();
		String sr_end = scheduleRepeat.getSr_end();
		String sr_repeat = scheduleRepeat.getSr_repeat();

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Calendar calSrStart = Calendar.getInstance();
		Calendar calSrEnd = Calendar.getInstance();

		try {
			calSrStart.setTime(timeFormat.parse(sr_start)); // 근무 시작 시간
			calSrEnd.setTime(timeFormat.parse(sr_end)); // 근무 종료 시간
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (calSrEnd.before(calSrStart)) {
			calSrEnd.add(Calendar.DATE, 1); // 근무 종료 시간이 근무 시작 시간보다 빠를 때 하루 더해줌
		}

		int interval = (int) (calSrEnd.getTimeInMillis() - calSrStart.getTimeInMillis()); // 시작시간과 종료시간의 차이를 밀리초 단위로 구함

		// 오늘부터 다음달 말일까지의 날짜를 구하기 위한 Calendar 객체 선언
		Calendar now = Calendar.getInstance();
		Calendar repeatEnd = Calendar.getInstance();

		repeatEnd.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1, 0, 0, 0);
		repeatEnd.add(Calendar.MONTH, 2);

		log.info("now: " + now.getTime());
		log.info("repeatEnd: " + repeatEnd.getTime());

		String[] dayArr = getDayArr(sr_repeat); // 요일별로 반복문 돌기 위해 요일 문자열 배열로 변환

		ScheduleVO scheduleVo = new ScheduleVO(); // scheduleService에 넘길 ScheduleVO 객체 생성

		scheduleVo.setC_code(c_code);
		scheduleVo.setU_id(u_id);
		scheduleVo.setSr_code(sr_code);

		int count = 0; // 총 applySchedule 횟수

		for (int i = 0; i < dayArr.length; i++) { // 요일 별 반복문

			Calendar calStart = (Calendar) now.clone(); // 근무 시작 날짜와 시간

			calStart.set(Calendar.HOUR_OF_DAY, calSrStart.get(Calendar.HOUR_OF_DAY));
			calStart.set(Calendar.MINUTE, calSrStart.get(Calendar.MINUTE));

			int dayArr_int = Integer.parseInt(dayArr[i]);

			log.info("dayArrLoopStart" + ForLog.dot);
			log.info("dayArr_int: " + dayArr_int);
			log.info("calStartDefault: " + calStart.toString());

			calStart.set(Calendar.DAY_OF_WEEK, dayArr_int);

			log.info("calStartSetDayOfWeek: " + calStart.getTime());

			Calendar calEnd = (Calendar) calStart.clone(); // 근무 종료 날짜와 시간

			log.info("calEndClonedBycalStart: " + calEnd.getTime());

			calEnd.add(Calendar.MILLISECOND, interval);

			log.info("calEndAddedInterval: " + calEnd.getTime());

			if (dayArr_int > now.get(Calendar.DAY_OF_WEEK)) { // 주어진 요일이 오늘의 요일보다 늦다면 그 날엔 일정 추가

				scheduleVo.setS_start(dateFormat.format(calStart.getTime()));
				scheduleVo.setS_end(dateFormat.format(calEnd.getTime()));

				count += scheduleMapper.insert(scheduleVo);
			}

			calStart.add(Calendar.DAY_OF_MONTH, 7); // 일주일씩 추가
			calEnd.add(Calendar.DAY_OF_MONTH, 7);

			log.info("calStartAddedWeek: " + calStart.getTime());
			log.info("calEndAddedWeek: " + calEnd.getTime());

			while (repeatEnd.after(calStart)) { // 시작 날짜가 다다음달 1일보다 빠른동안 반복

				scheduleVo.setS_start(dateFormat.format(calStart.getTime()));
				scheduleVo.setS_end(dateFormat.format(calEnd.getTime()));

				count += scheduleMapper.insert(scheduleVo);

				calStart.add(Calendar.DAY_OF_MONTH, 7);
				calEnd.add(Calendar.DAY_OF_MONTH, 7);

				log.info("calStartAddedWeek: " + calStart.getTime());
				log.info("calEndAddedWeek: " + calEnd.getTime());
			}
		}
		return count;
	}

	public String[] getDayArr(String repeat) {
		return repeat == null ? new String[] {} : repeat.split("");
	}

	// @Scheduled(cron = "0/3 * * * * *")
	public void testtask() {
		log.info("test");
	}

}
