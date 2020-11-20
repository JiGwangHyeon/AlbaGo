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

import com.albago.domain.WageVO;
import com.albago.service.WageService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@AllArgsConstructor
@Log4j
@RequestMapping("/salary")
public class SalaryController {

	private WageService service;

	@GetMapping(value = "/getList/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<WageVO>> getWageList(@PathVariable("c_code") long c_code, HttpServletRequest request) {
		log.info("getWageList 호출.....................");

		WageVO wage = new WageVO();

		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		wage.setU_id(u_id);
		wage.setC_code(c_code);

		return new ResponseEntity<>(service.getWageList(wage), HttpStatus.OK); // 계정 삭제 결과 반환, 1이면 정상
	}

}
