package com.albago.controller;

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

import com.albago.domain.ScheduleChangeVO;
import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;
import com.albago.service.ScheduleService;
import com.albago.util.ForLog;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/schedule")
@Log4j
@AllArgsConstructor
public class ScheduleController {

	private ScheduleService scheduleService;

	// ***********************************스케줄 기본
	// 기능********************************************//

	// 해당 직원의 오늘의 근무 일정 조회
	@GetMapping(value = "/todays/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ScheduleVO> getTodaysSchedule(@PathVariable("c_code") long c_code,
			HttpServletRequest request) {

		log.info("getTodaysSchedule 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setC_code(c_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.getTodaysSchedule(schedule), HttpStatus.OK);// 오늘의 일정이 담긴 vo 반환
	}

	// s_code로 스케줄 조회
	@GetMapping(value = "/get/schedule/{s_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ScheduleVO> getSchedule(@PathVariable("s_code") int s_code, HttpServletRequest request) {

		log.info("getSchedule 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setS_code(s_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.getScheduleSingle(schedule), HttpStatus.OK);// 오늘의 일정이 담긴 vo 반환
	}

	// 출근
	@GetMapping(value = "/arrive/{s_code}/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> arrive(@PathVariable("s_code") int s_code, @PathVariable("c_code") long c_code,
			HttpServletRequest request) {

		log.info("arrive 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setS_code(s_code);
		schedule.setC_code(c_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.arrive(schedule), HttpStatus.OK);

	}

	// 퇴근
	@GetMapping(value = "/leave/{s_code}/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> leave(@PathVariable("s_code") int s_code, @PathVariable("c_code") long c_code,
			HttpServletRequest request) {

		log.info("leave 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setS_code(s_code);
		schedule.setC_code(c_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.leave(schedule), HttpStatus.OK);
	}

	// 주별 일정 불러오기
	@GetMapping(value = "/week/{c_code}/{week}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<ScheduleVO>> getWeekScheduleForE(@PathVariable("c_code") long c_code,
			@PathVariable("week") int week, HttpServletRequest request) {

		log.info("getWeekScheduleForE 호출............");

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setC_code(c_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.getScheduleWeek(schedule, week), HttpStatus.OK);
	}

	// 월별 일정 불러오기
	@GetMapping(value = "/month/{c_code}/{year}/{month}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<ScheduleVO>> getMonthScheduleForE(@PathVariable("c_code") long c_code,
			@PathVariable("year") int year, @PathVariable("month") int month, HttpServletRequest request) {

		log.info("getMonthScheduleForE 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setC_code(c_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.getScheduleMonth(schedule, year, month), HttpStatus.OK);
	}

	// *******************************근무 신청************************************//

	// 근무 신청
	@GetMapping(value = "/add/{c_code}/{s_start:.+}/{s_end:.+}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> insertOnce(@PathVariable("c_code") long c_code,
			@PathVariable("s_start") String s_start, @PathVariable("s_end") String s_end, HttpServletRequest request) {

		log.info("insertOnce 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setC_code(c_code);
		schedule.setU_id(u_id);
		schedule.setS_start(s_start);
		schedule.setS_end(s_end);

		return new ResponseEntity<>(scheduleService.applySchedule(schedule), HttpStatus.OK);
	}

	// 근무 신청 내용 수정하기
	@GetMapping(value = "/editApply/{s_code}/{s_start}/{s_end}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> editAppliedSchedule(@PathVariable("s_code") int s_code,
			@PathVariable("s_start") String s_start, @PathVariable("s_end") String s_end, HttpServletRequest request) {

		log.info("editAppliedSchedule 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setS_code(s_code);
		schedule.setU_id(u_id);
		schedule.setS_start(s_start);
		schedule.setS_end(s_end);

		return new ResponseEntity<>(scheduleService.editAppliedSchedule(schedule), HttpStatus.OK);
	}

	// 근무 신청 취소하기
	@GetMapping(value = "/cancel/{s_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> cancelAppliedSchedule(@PathVariable("s_code") int s_code,
			HttpServletRequest request) {

		log.info("cancelAppliedSchedule 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setS_code(s_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.cancelAppliedSchedule(schedule), HttpStatus.OK);
	}

	// ***********************************반복근무****************************************//

	// 반복 근무 신청
	@GetMapping(value = "/add/repeat/{c_code}/{repeat}/{start}/{end}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> insertWeeklyRepeat(@PathVariable("c_code") long c_code,
			@PathVariable("repeat") String repeat, @PathVariable("start") String start, @PathVariable("end") String end,
			HttpServletRequest request) {

		log.info("insertWeeklyRepeat 호출" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setC_code(c_code);
		scheduleRepeat.setU_id(u_id);
		scheduleRepeat.setSr_start(start);
		scheduleRepeat.setSr_end(end);
		scheduleRepeat.setSr_repeat(repeat);

		return new ResponseEntity<>(scheduleService.applyRepeatedSchedule(scheduleRepeat), HttpStatus.OK);
	}

	// 신청한 반복 근무 조회
	@GetMapping(value = "/get/repeat/{sr_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ScheduleRepeatVO> lookUpAppliedRepeatedSchedule(@PathVariable("sr_code") int sr_code,
			HttpServletRequest request) {

		log.info("lookUpAppliedRepeatedSchedule" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setSr_code(sr_code);
		scheduleRepeat.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.lookUpAppliedRepeatedSchedule(scheduleRepeat), HttpStatus.OK);
	}

	// 반복 근무 신청 내용 수정
	@GetMapping(value = "/edit/repeat/{sr_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> editAppliedRepeatedSchedule(@PathVariable("sr_code") int sr_code,
			HttpServletRequest request) {

		log.info("editAppliedRepeatedSchedule" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setSr_code(sr_code);
		scheduleRepeat.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.editAppliedRepeatedSchedule(scheduleRepeat), HttpStatus.OK);
	}

	// 반복 근무 신청 내용 수정
	@GetMapping(value = "/cancel/repeat/{sr_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> cancelAppliedRepeatedSchedule(@PathVariable("sr_code") int sr_code,
			HttpServletRequest request) {

		log.info("cancelAppliedRepeatedSchedule" + ForLog.dot);

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setSr_code(sr_code);
		scheduleRepeat.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.cancelAppliedRepeatedSchedule(scheduleRepeat), HttpStatus.OK);
	}

	// *******************근무 변경******************************//

	// 근무 일정 변경 요청
	@GetMapping(value = "/change/insert/{s_code}/{sc_start}/{sc_end}/{sc_reason}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int insertScheduleChange(@PathVariable("s_code") int s_code, @PathVariable("sc_start") String sc_start,
			@PathVariable("sc_end") String sc_end, @PathVariable("sc_reason") String sc_reason) {

		log.info("insertScheduleChange.........................");

		ScheduleChangeVO scheduleChange = new ScheduleChangeVO();

		scheduleChange.setS_code(s_code);
		scheduleChange.setSc_start(sc_start);
		scheduleChange.setSc_end(sc_end);
		scheduleChange.setSc_reason(sc_reason);

		return scheduleService.applyScheduleChange(scheduleChange);
	}

	// 근무 일정 변경 요청 내용 변경
	/*
	 * @GetMapping(value =
	 * "/change/modify/{s_code}/{sc_start}/{sc_end}/{sc_reason}", produces = {
	 * MediaType.APPLICATION_JSON_UTF8_VALUE }) public int
	 * modifyScheduleChange(@PathVariable("s_code") int
	 * s_code, @PathVariable("sc_start") String sc_start,
	 * 
	 * @PathVariable("sc_end") String sc_end, @PathVariable("sc_reason") String
	 * sc_reason) {
	 * 
	 * log.info("insertScheduleChange.........................");
	 * 
	 * ScheduleChangeVO scheduleChange = new ScheduleChangeVO();
	 * 
	 * scheduleChange.setS_code(s_code); scheduleChange.setSc_start(sc_start);
	 * scheduleChange.setSc_end(sc_end); scheduleChange.setSc_reason(sc_reason);
	 * 
	 * return scheduleChangeService.modifyRequest(scheduleChange); }
	 */

	// 근무 일정 변경 요청 내용 조회
	@GetMapping(value = "/change/get/{s_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ScheduleChangeVO getScheduleChange(@PathVariable("s_code") int s_code) {

		log.info("insertScheduleChange.........................");

		return scheduleService.lookUpAppliedScheduleChange(s_code);
	}

	// 근무 일정 변경 요청 취소
	@GetMapping(value = "/change/cancel/{s_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int canselScheduleChange(@PathVariable("s_code") int s_code) {

		log.info("cancelScheduleChange.........................");

		return scheduleService.cancelScheduleChange(s_code);
	}

	// *******************근무 삭제******************************//

	// 근무 삭제 신청
	@GetMapping(value = "/delete/apply/{s_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> applyToCancelSchedule(@PathVariable("s_code") int s_code,
			HttpServletRequest request) {

		log.info("applyToCancelSchedule");

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setS_code(s_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.applyToCancelSchedule(schedule), HttpStatus.OK);
	}

	// 근무 삭제 신청 취소

	@GetMapping(value = "/delete/cancel/{s_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> cancelToCancelSchedule(@PathVariable("s_code") int s_code,
			HttpServletRequest request) {

		log.info("cancelToCancelSchedule.........................");

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleVO schedule = new ScheduleVO();

		schedule.setS_code(s_code);
		schedule.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.cancelToCancelSchedule(schedule), HttpStatus.OK);
	}

	// *******************반복근무 삭제******************************//

	// 반복근무 삭제 신청
	@GetMapping(value = "/delete/repeat/apply/{sr_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> applyToCancelRepeatedSchedule(@PathVariable("sr_code") int sr_code,
			HttpServletRequest request) {

		log.info("applyToCancelRepeatedSchedule");

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setSr_code(sr_code);
		scheduleRepeat.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.applyToCancelRepeatedSchedule(scheduleRepeat), HttpStatus.OK);
	}

	// 반복근무 삭제 신청 취소

	@GetMapping(value = "/delete/repeat/cancel/{sr_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> cancelToCancelRepeatedSchedule(@PathVariable("sr_code") int sr_code,
			HttpServletRequest request) {

		log.info("cancelToCancelRepeatedSchedule.........................");

		HttpSession session = request.getSession();

		String u_id = (String) session.getAttribute("u_id");

		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setSr_code(sr_code);
		scheduleRepeat.setU_id(u_id);

		return new ResponseEntity<>(scheduleService.cancelToCancelRepeatedSchedule(scheduleRepeat), HttpStatus.OK);
	}
}
