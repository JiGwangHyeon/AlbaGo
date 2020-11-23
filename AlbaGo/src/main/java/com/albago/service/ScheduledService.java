package com.albago.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;
import com.albago.mapper.ScheduleMapper;
import com.albago.util.ForLog;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class ScheduledService {

	private ScheduleMapper scheduleMapper;

	// 매 월 1일 00시 00분 일정 자동 추가
	public int scheduledRepeatInsert(ScheduleRepeatVO scheduleRepeat) {

		log.info("scheduledRepeatInsert" + ForLog.dot);
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

		// 다음달 1일부터 다음달 말일까지의 날짜를 구하기 위한 Calendar 객체 선언
		Calendar repeatStart = Calendar.getInstance();

		repeatStart.set(repeatStart.get(Calendar.YEAR), repeatStart.get(Calendar.MONTH), 1, 0, 0, 0);
		repeatStart.add(Calendar.MONTH, 1);

		Calendar repeatEnd = (Calendar) repeatStart.clone();
		repeatEnd.add(Calendar.MONTH, 1);

		log.info("repeatStart: " + repeatStart.getTime());
		log.info("repeatEnd: " + repeatEnd.getTime());

		String[] dayArr = (sr_repeat == null) ? new String[] {} : sr_repeat.split("");// 요일별로 반복문 돌기 위해 요일 문자열 배열로 변환

		ScheduleVO scheduleVo = new ScheduleVO(); // scheduleService에 넘길 ScheduleVO 객체 생성

		scheduleVo.setC_code(c_code);
		scheduleVo.setU_id(u_id);
		scheduleVo.setSr_code(sr_code);

		int count = 0; // 총 applySchedule 횟수

		for (int i = 0; i < dayArr.length; i++) { // 요일 별 반복문

			Calendar calStart = (Calendar) repeatStart.clone(); // 근무 시작 날짜와 시간

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

			if (dayArr_int > repeatStart.get(Calendar.DAY_OF_WEEK)) { // 주어진 요일이 오늘의 요일보다 늦다면 그 날엔 일정 추가

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

	// 퇴근까지 기록된 schedule에 대해
}
