package com.albago.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albago.domain.ScheduleVO;
import com.albago.service.ScheduleService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/schedule")
@Log4j
@AllArgsConstructor
public class ScheduleController {

	private ScheduleService scheduleService;

	// 해당 직원의 오늘의 근무 일정 조회
	@GetMapping(value = "/todays/{c_code}/{u_id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ScheduleVO getTodaysScheduleForE(@PathVariable("c_code") int c_code, @PathVariable("u_id") String u_id) {
		log.info("getTodaysSchedule 호출.........................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);
		schedule.setU_id(u_id);

		return scheduleService.getTodaysScheduleForE(schedule);// 오늘의 일정이 담긴 vo 반환
	}

	// 출근
	@GetMapping(value = "/arrive/{s_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int arrive(@PathVariable("s_code") int s_code) {
		log.info("arrive 호출............");

		return scheduleService.arrive(s_code);
	}

	// 퇴근
	@GetMapping(value = "/leave/{s_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int leave(@PathVariable("s_code") int s_code) {
		log.info("leave 호출............");

		return scheduleService.leave(s_code);
	}

	// 주별 일정 불러오기
	@GetMapping(value = "/week/{c_code}/{u_id}/{week}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public List<ScheduleVO> getWeekScheduleForE(@PathVariable("c_code") int c_code, @PathVariable("u_id") String u_id,
			@PathVariable("week") int week) {
		log.info("getWeekScheduleForE 호출............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);
		schedule.setU_id(u_id);
		schedule.setWeek(week);

		return scheduleService.getWeekScheduleForE(schedule);
	}

	// 월별 일정 불러오기
	@GetMapping(value = "/month/{c_code}/{u_id}/{year}/{month}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public List<ScheduleVO> getMonthScheduleForE(@PathVariable("c_code") int c_code, @PathVariable("u_id") String u_id,
			@PathVariable("year") String year, @PathVariable("month") String month) {
		log.info("getMonthScheduleForE 호출............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);
		schedule.setU_id(u_id);
		schedule.setYear(year);
		schedule.setMonth(month);

		return scheduleService.getMonthScheduleForE(schedule);
	}

	// 일정 한개만 추가
	@GetMapping(value = "/add/{c_code}/{u_id}/{year}/{month}/{date}/{start_hour}/{start_minute}/{end_hour}/{end_minute}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int insertOnce(@PathVariable("c_code") int c_code, @PathVariable("u_id") String u_id,
			@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("date") int date,
			@PathVariable("start_hour") int start_hour, @PathVariable("start_minute") int start_minute,
			@PathVariable("end_hour") int end_hour, @PathVariable("end_minute") int end_minute) {
		log.info("insertOnce 호출............");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);
		schedule.setU_id(u_id);
		Calendar start_date;
		Calendar end_date;
		start_date.getInstance();
		start_date.set(year, month, date, start_hour, start_minute, 0);

		end_date.getInstance();
		end_date.set(year, month, date, end_hour, end_minute, 0);

		if (start_date.after(end_date)) {

		}

		String start_df = "d";
		int start_time = start_hour * 24 + start_minute;
		int end_time = end_hour * 24 + end_minute;
		if (start_time > end_time) {

		}

		return scheduleService.insertOnce(schedule);
	}

}
