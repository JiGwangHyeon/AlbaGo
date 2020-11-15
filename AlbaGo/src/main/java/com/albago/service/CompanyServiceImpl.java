package com.albago.service;

import org.springframework.stereotype.Service;

import com.albago.mapper.CompanyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class CompanyServiceImpl implements CompanyService {

	private CompanyMapper mapper;

	@Override
	public String getCompanyName(int c_code) {
		log.info("getCompanyName..................");

		return mapper.getCompanyName(c_code);
	}
}
