package com.albago.controller;

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
	@GetMapping(value = "/todays/{c_code}/{u_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ScheduleVO getTodaysSchedule(@PathVariable("c_code") int c_code, @PathVariable("u_id") String u_id) {
		log.info("getTodaysSchedule 호출.........................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setC_code(c_code);
		schedule.setU_id(u_id);

		return scheduleService.getTodaysSchedule(schedule);// 오늘의 일정이 담긴 vo 반환
	}
}
