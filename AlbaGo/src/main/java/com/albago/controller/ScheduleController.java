package com.albago.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;
import com.albago.service.ScheduleRepeatService;
import com.albago.service.ScheduleService;
import com.albago.util.RepeatSchedule;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/schedule")
@Log4j
@AllArgsConstructor
public class ScheduleController {

	private ScheduleService scheduleService;
	private ScheduleRepeatService scheduleRepeatService;

	// 해당 직원의 오늘의 근무 일정 조회
	@GetMapping(value = "/todays/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ScheduleVO> getTodaysScheduleForE(@PathVariable("c_code") long c_code,
			HttpServletRequest request) {
		log.info("getTodaysSchedule 호출.........................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);

		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.getTodaysScheduleForE(schedule), HttpStatus.OK);// 오늘의 일정이 담긴 vo 반환
	}

	// 출근
	@GetMapping(value = "/arrive/{s_code}/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> arrive(@PathVariable("s_code") int s_code, @PathVariable("c_code") long c_code,
			HttpServletRequest request) {
		log.info("arrive 호출............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setS_code(s_code);
		schedule.setC_code(c_code);
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.arrive(schedule), HttpStatus.OK);

	}

	// 퇴근
	@GetMapping(value = "/leave/{s_code}/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> leave(@PathVariable("s_code") int s_code, @PathVariable("c_code") long c_code,
			HttpServletRequest request) {
		log.info("leave 호출............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setS_code(s_code);
		schedule.setC_code(c_code);
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.leave(schedule), HttpStatus.OK);
	}

	// 주별 일정 불러오기
	@GetMapping(value = "/week/{c_code}/{week}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<ScheduleVO>> getWeekScheduleForE(@PathVariable("c_code") long c_code,
			@PathVariable("week") int week, HttpServletRequest request) {
		log.info("getWeekScheduleForE 호출............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		schedule.setU_id(u_id);

		Calendar s_start = Calendar.getInstance();
		if (s_start.get(Calendar.DAY_OF_WEEK) == 1) {
			s_start.add(Calendar.DATE, -6 + week * 7);
		} else {
			s_start.add(Calendar.DATE, -s_start.get(Calendar.DAY_OF_WEEK) + 2 + week * 7);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		schedule.setS_start(sdf.format(s_start.getTime()));

		return new ResponseEntity<>(scheduleService.getWeekScheduleForE(schedule), HttpStatus.OK);
	}

	// 월별 일정 불러오기
	@GetMapping(value = "/month/{c_code}/{year}/{month}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<ScheduleVO>> getMonthScheduleForE(@PathVariable("c_code") long c_code,
			@PathVariable("year") int year, @PathVariable("month") int month, HttpServletRequest request) {
		log.info("getMonthScheduleForE 호출............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		schedule.setU_id(u_id);

		Calendar s_start = Calendar.getInstance();
		s_start.set(Calendar.YEAR, year);
		s_start.set(Calendar.MONTH, month - 1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		schedule.setS_start(sdf.format(s_start.getTime()));
		return new ResponseEntity<>(scheduleService.getMonthScheduleForE(schedule), HttpStatus.OK);
	}

	// 일정 한개만 추가
	@GetMapping(value = "/add/{c_code}/{s_start:.+}/{s_end:.+}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> insertOnce(@PathVariable("c_code") long c_code,
			@PathVariable("s_start") String s_start, @PathVariable("s_end") String s_end, HttpServletRequest request) {
		log.info("insertOnce 호출............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		schedule.setU_id(u_id);
		schedule.setS_start(s_start);
		schedule.setS_end(s_end);

		return new ResponseEntity<>(scheduleService.insertSchedule(schedule), HttpStatus.OK);
	}

	// 일정 매 주 반복 추가
	@GetMapping(value = "/add/repeat/{c_code}/{repeat}/{start}/{end}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> insertWeeklyRepeat(@PathVariable("c_code") long c_code,
			@PathVariable("repeat") String repeat, @PathVariable("start") String start, @PathVariable("end") String end,
			HttpServletRequest request) throws ParseException {
		log.info("insertWeeklyRepeat 호출............");

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setC_code(c_code);
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		scheduleRepeat.setU_id(u_id);
		scheduleRepeat.setSr_start(start);
		scheduleRepeat.setSr_end(end);
		scheduleRepeat.setSr_repeat(repeat);

		int result = scheduleRepeatService.insertRepeat(scheduleRepeat);

		if (result == 1) {
			RepeatSchedule rs = new RepeatSchedule();
			result = rs.whenInsert(scheduleRepeat, scheduleService);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
