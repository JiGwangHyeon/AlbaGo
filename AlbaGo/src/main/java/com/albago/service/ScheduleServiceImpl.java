package com.albago.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleChangeVO;
import com.albago.domain.ScheduleRepeatVO;
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

	// ***********************근무 일정 반복**************************//

	@Override
	public int applyRepeatedSchedule(ScheduleRepeatVO scheduleRepeat) {
		log.info("applyRepeatedSchedule" + ForLog.dot);

		int dupCnt = checkDuplicateRepeat(scheduleRepeat);

		if (dupCnt > 0) {
			log.info("Duplicated Repeat Count: " + dupCnt);
			return 0;
		} else if (dupCnt < 0) {
			log.info("Duplicated Schedule Exist.");
			return -1;
		}

		int result = scheduleRepeatMapper.insert(scheduleRepeat);

		if (result == 1) {
			whenInsert(scheduleRepeat);
		}
		return result;
	}

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
			return -1;
		}

		int cntDup = 0;

		List<ScheduleRepeatVO> scheduleRepeatExist = scheduleRepeatMapper.getCountDuplicated(scheduleRepeat);

		for (ScheduleRepeatVO exist : scheduleRepeatExist) {
			String[] rDayListEx = exist.getSr_repeat().split("");

			String srStartEx = exist.getSr_start();
			String srEndEx = exist.getSr_end();

			if (checkDuplicateDay(rDayList, rDayListEx) && checkTime(srStart, srStartEx, srEnd, srEndEx)) {
				cntDup += 1;
			}
		}

		if (cntDup == 0) {
			log.info("There's No Duplicated.");
		} else {
			log.info("Duplicated Count: " + cntDup);
		}

		return cntDup;
	}

	public boolean checkDuplicateDay(String[] rDayList, String[] rDayListEx) {
		log.info("checkDuplicateDay Start.................................");
		for (String i : rDayList) {
			for (String j : rDayListEx) {
				log.info("checkDuplicate: " + i + ", " + j);
				if (i.equals(j)) {
					log.info("checkDuplicateDay End/return true......................");
					return true;
				}
			}
		}

		log.info("checkDuplicateDay End/return false......................");
		return false;
	}

	public boolean checkTime(String srStart, String srStartEx, String srEnd, String srEndEx) {
		log.info("checkTime Start......................");
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

	// ***********************근무 변경 신청**************************//

	@Override
	public boolean isWritable(int s_code) {
		log.info("isWritable.................");

		if (scheduleChangeMapper.getCountByScodeStatIsW(s_code) == 0)
			return true;

		return false;
	}

	@Override
	public int applyChangeSchedule(ScheduleChangeVO scheduleChange) {
		log.info("requestChangeSchedule..............");

		if (isWritable(scheduleChange.getS_code())) {
			return scheduleChangeMapper.insert(scheduleChange);
		}

		return 0;
	}

	/*
	 * @Override public int modifyRequest(ScheduleChangeVO scheduleChange) {
	 * log.info("modifyRequest.......................");
	 * 
	 * return mapper.update(scheduleChange); }
	 */

	@Override
	public ScheduleChangeVO getApplySingle(int s_code) {
		log.info("getRequest............................");

		return scheduleChangeMapper.select(s_code);
	}

	@Override
	public int cancelRequest(int s_code) {
		log.info("cancelRequest......................");

		return scheduleChangeMapper.delete(s_code);
	}

//	@Scheduled(cron = "0/3 * * * * *")
	public void testtask() {
		log.info("test");
	}

}
