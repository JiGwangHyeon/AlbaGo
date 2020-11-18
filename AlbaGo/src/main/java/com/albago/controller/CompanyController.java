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

import com.albago.domain.CompanyVO;
import com.albago.service.CompanyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/company")
@Log4j
@AllArgsConstructor
public class CompanyController {

	private CompanyService companyService;

	@GetMapping(value = "/getName/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public String getCompanyName(@PathVariable("c_code") int c_code) {
		log.info("getCompanyName 호출......................");

		return companyService.getCompanyName(c_code);
	}

	@GetMapping(value = "/getListById", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<CompanyVO>> getCompanyListById(HttpServletRequest request) {
		log.info("getCompanyName 호출......................");

		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(companyService.getCompanyListById(u_id), HttpStatus.OK); // 계정 삭제 결과 반환, 1이면 정상
	}
}
