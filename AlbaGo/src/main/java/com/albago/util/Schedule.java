package com.albago.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;
import com.albago.mapper.ScheduleMapper;
import com.albago.mapper.ScheduleRepeatMapper;
import com.albago.service.ScheduleRepeatService;

import lombok.extern.log4j.Log4j;

@Log4j
public class Schedule {

	private ScheduleRepeatService scheduleRepeatService;

	private ScheduleRepeatMapper scheduleRepeatMapper;
	private ScheduleMapper scheduleMapper;

	// 일정 매 주 반복 추가 컨트롤러
	public void insertWeeklyRepeat(@PathVariable("c_code") long c_code, @PathVariable("repeat") String repeat,
			@PathVariable("start") String start, @PathVariable("end") String end, HttpServletRequest request)
			throws ParseException {
		// 넘길 scheduleRepeatVO 객체 생성
		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		// session에서 id 가져오기
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");

		// scheduleRepeatVO에 값 담기
		scheduleRepeat.setU_id(u_id);
		scheduleRepeat.setC_code(c_code);
		scheduleRepeat.setSr_start(start);
		scheduleRepeat.setSr_end(end);
		scheduleRepeat.setSr_repeat(repeat);

		// schedule_repeat테이블에 값 insert
		scheduleRepeatService.insertRepeat(scheduleRepeat);
	}

	// serviceImpl에 들어갈 override method
	public int insertRepeat(ScheduleRepeatVO scheduleRepeat) throws ParseException {

		// 일정 겹치는 건 있는지 확인, 0일 때 정상
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

	// serviceImpl에 들어갈 method
	public int checkDuplicateRepeat(ScheduleRepeatVO scheduleRepeat) {
		// return = -1 : 기존의 schedule과 겹침
		// return = 0 : 겹치는 것 없음
		// return > 0 : 겹치는 scheduleRepeat 개수
		log.info("checkDuplicateRepeat........................");

		// 입력 받은 요일을 한글자씩 파싱해서 배열에 저장
		String[] rDayList = scheduleRepeat.getSr_repeat().split("");
		// 입력 받은 근무 시작 시간
		String srStart = scheduleRepeat.getSr_start();
		// 입력 받은 근무 종료 시간
		String srEnd = scheduleRepeat.getSr_end();

		// 입력받은 근무 시작시간이 종료시간보다 뒤일 때 종료시간에 하루를 더해주기 위한 부분
		Calendar sst = Calendar.getInstance(); // 근무 시작 시간
		Calendar set = Calendar.getInstance(); // 근무 종료 시간

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		try {
			// 입력 받은 근무 시작 시간을 sdf에 맞게 변경
			sst.setTime(sdf.parse(srStart));
			// 입력 받은 근무 종료 시간을 sdf에 맞게 변경
			set.setTime(sdf.parse(srEnd));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//
		if (sst.after(set)) // 근무 시작 시간이 종료 시간보다 뒤일 경우
			set.add(Calendar.DATE, 1); // 근무 종료 시간에 하루 더함
		long millis = set.getTimeInMillis() - sst.getTimeInMillis(); // 근무 시간 구함

		boolean dup = false;

		for (String i : rDayList) { // 입력 받은 요일 하나씩 for문 돌림
			ScheduleVO schedule = new ScheduleVO(); // 중복되는 schedule 찾기 위해 scheduleVO 객체 생성
			schedule.setC_code(scheduleRepeat.getC_code()); // ScheduleRepeatVO 객체에서 c_code 가져옴
			schedule.setU_id(scheduleRepeat.getU_id()); // ScheduleRepeatVO 객체에서 u_id 가져옴
			schedule.setSr_code(Integer.parseInt(i)); // rDayList에서 각 요일 가져옴
			List<ScheduleVO> list = scheduleMapper.selectMultiByCcodeIdStart(schedule); // 스케줄 테이블에서 sr_code, u_id,
																						// c_code가 일치하는 스케줄 가져와서
																						// List<ScheduleVO>에 담음
			for (ScheduleVO l : list) { // scheduleVO 하나씩 탐색
				Calendar sse = Calendar.getInstance(); // 기존의 s_start 담을 Calendar 객체 생성
				Calendar see = Calendar.getInstance(); // 기존의 s_end 담을 Calendar 객체 생성

				try {
					sse.setTime(sdf2.parse(l.getS_start())); // sse에 시간 담음
					see.setTime(sdf2.parse(l.getS_end())); // see에 시간 담음
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Calendar ss = (Calendar) sse.clone(); // 입력 할 s_start 담을 Calendar 객체 생성
				ss.set(Calendar.HOUR_OF_DAY, sst.get(Calendar.HOUR_OF_DAY)); // 시간 설정
				ss.set(Calendar.MINUTE, sst.get(Calendar.MINUTE));
				ss.set(Calendar.SECOND, sst.get(Calendar.SECOND));

				Calendar se = (Calendar) ss.clone(); // 입력 할 s_end 담을 Calendar 객체 생성
				se.add(Calendar.MILLISECOND, (int) millis); // 근무 시간 차이로 s_end 구함

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
				} // 여러 조건으로 일정이 서로 겹치는지 확인, 겹치는거 하나라도 존재하면 for문 탈출
			}
			if (dup) {
				break;
			}
		}
		if (dup) {
			return -1;// 기존의 schedule과 겹친다면 -1 반환
		}

		int cntDup = 0; // 겹치는 scheduleRepeat 개수 담을 변수 선언

		List<ScheduleRepeatVO> scheduleRepeatExist = scheduleRepeatMapper.getListForCheckDuplicate(scheduleRepeat); // 비교할
																													// 기존
																													// scheduleRepeat
																													// List<ScheduleRepeatVO>로
																													// 불러옴

		for (ScheduleRepeatVO exist : scheduleRepeatExist) { // 리스트 for문으로 하나씩 탐색

			String[] rDayListEx = exist.getSr_repeat().split(""); // 기존 sr_repeat

			String srStartEx = exist.getSr_start();
			String srEndEx = exist.getSr_end();

			if (checkDuplicateDay(rDayList, rDayListEx) && checkTime(srStart, srStartEx, srEnd, srEndEx)) { // 요일 겹치고
																											// 시간도 겹치면
				cntDup += 1; // cntDup +1
			}
		}

		if (cntDup == 0) {
			log.info("There's No Duplicated.");
		} else {
			log.info("Duplicated Count: " + cntDup);
		}

		return cntDup;
	}

	// serviceImpl에 들어갈 method
	public boolean checkDuplicateDay(String[] rDayList, String[] rDayListEx) { // 요일 배열 전체 비교
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

	// serviceImpl에 들어갈 method
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

	// serviceImpl에 들어갈 method
	public int whenInsert(ScheduleRepeatVO scheduleRepeat) throws ParseException {

		int sr_code = scheduleRepeat.getSr_code();
		long c_code = scheduleRepeat.getC_code();
		String u_id = scheduleRepeat.getU_id();
		String sr_start = scheduleRepeat.getSr_start();
		String sr_end = scheduleRepeat.getSr_end();
		String sr_repeat = scheduleRepeat.getSr_repeat();

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date time_start = timeFormat.parse(sr_start);
		System.out.println(time_start);
		Date time_end = timeFormat.parse(sr_end);
		if (time_end.before(time_start)) {
			long millis = time_end.getTime();
			time_end.setTime(millis + 86400000);
		}

		Calendar now = Calendar.getInstance();
		System.out.println("now: " + now.getTime());
		Calendar end = Calendar.getInstance();
		end.set(0, 0, 0, 0, 0, 0);
		end.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);
		end.add(Calendar.MONTH, 2);
		System.out.println("end: " + end.getTime());

		int interval = (int) (time_end.getTime() - time_start.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String[] dayArr = getDayArr(sr_repeat);

		ScheduleVO scheduleVo = new ScheduleVO();
		scheduleVo.setC_code(c_code);
		scheduleVo.setU_id(u_id);
		scheduleVo.setSr_code(sr_code);

		int count = 0;
		for (int i = 0; i < dayArr.length; i++) {
			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			System.out.println("calStart: " + calStart.getTime());
			System.out.println("calEnd: " + calEnd.getTime());

			calStart.setTime(time_start);
			System.out.println("now: " + now.getTime());
			calStart.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

			int dayArr_int = Integer.parseInt(dayArr[i]);

			System.out.println("dayArr_int: " + dayArr_int);
			System.out.println("calStart: " + calStart.getTime());
			calStart.set(Calendar.DAY_OF_WEEK, dayArr_int);
//			System.out.println("calStart: " + calStart.getTime());
			calEnd = (Calendar) calStart.clone();
			calEnd.add(Calendar.MILLISECOND, interval);
			System.out.println("calEnd: " + calEnd.getTime());

			if (now.get(Calendar.DAY_OF_WEEK) < dayArr_int) {

				scheduleVo.setS_start(sdf.format(calStart.getTime()));
				scheduleVo.setS_end(sdf.format(calEnd.getTime()));

				count += scheduleMapper.insert(scheduleVo);
			}

			calStart.add(Calendar.DAY_OF_MONTH, 7);
			calEnd.add(Calendar.DAY_OF_MONTH, 7);
			while (end.after(calStart)) {
				scheduleVo.setS_start(sdf.format(calStart.getTime()));
				scheduleVo.setS_end(sdf.format(calEnd.getTime()));
				System.out.println("calStart: " + calStart.getTime());
				System.out.println("calEnd: " + calEnd.getTime());

				count += scheduleMapper.insert(scheduleVo);

				calStart.add(Calendar.DAY_OF_MONTH, 7);
				calEnd.add(Calendar.DAY_OF_MONTH, 7);
			}
		}
		return count;
	}

	// serviceImpl에 들어갈 method
	public String[] getDayArr(String repeat) {
		return repeat == null ? new String[] {} : repeat.split("");
	}
}
