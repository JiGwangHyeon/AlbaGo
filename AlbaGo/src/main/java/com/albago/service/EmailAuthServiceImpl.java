package com.albago.service;

import org.springframework.stereotype.Service;

import com.albago.domain.EmailAuthVO;
import com.albago.mapper.EmailAuthMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class EmailAuthServiceImpl implements EmailAuthService {

	private EmailAuthMapper mapper;

	@Override
	public int makeAuthCode(EmailAuthVO emailAuth) {
		log.info("makeAuthCode..............");

		return mapper.create(emailAuth);
	}

	@Override
	public int matchCode(EmailAuthVO emailAuth) {
		log.info("matchCode...............");

		return mapper.match(emailAuth);
	}

}
