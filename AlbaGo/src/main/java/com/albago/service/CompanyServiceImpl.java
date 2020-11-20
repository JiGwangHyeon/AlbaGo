package com.albago.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.CompanyVO;
import com.albago.mapper.CompanyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class CompanyServiceImpl implements CompanyService {

	private CompanyMapper mapper;

	@Override
	public String getCompanyName(long c_code) {
		log.info("getCompanyName..................");

		return mapper.getCompanyName(c_code);
	}

	@Override
	public List<CompanyVO> getCompanyListById(String u_id) {
		log.info("getCompanyListById................");

		return mapper.getCompanyListById(u_id);
	}

}
