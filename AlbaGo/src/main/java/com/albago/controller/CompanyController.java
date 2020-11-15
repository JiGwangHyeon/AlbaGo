package com.albago.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
