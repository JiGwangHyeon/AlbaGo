package com.albago.service;

import org.springframework.stereotype.Service;

import com.albago.domain.UserInfoVO;
import com.albago.mapper.UserInfoMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class UserInfoServiceImpl implements UserInfoService {

	private UserInfoMapper mapper;

	@Override
	public int register(UserInfoVO userInfo) {
		log.info("userInfo_insert............." + userInfo);

		return mapper.insertUser(userInfo);
	}

	@Override
	public UserInfoVO get(String u_id) {

		log.info("userInfo_get..........." + u_id);

		return mapper.readUser(u_id);
	}

	@Override
	public int login(UserInfoVO userInfo) {

		log.info("login.....................");

		return mapper.login(userInfo);
	}

	@Override
	public int beforeFindId(UserInfoVO userInfo) {

		log.info("beforeFindId.....................");

		return mapper.matchNameEmail(userInfo);
	}

	@Override
	public int beforeFindPw(UserInfoVO userInfo) {

		log.info("beforeFindPw.....................");

		return mapper.matchNameIdEmail(userInfo);
	}

	@Override
	public int resetPw(UserInfoVO userInfo) {

		log.info("resetPw.....................");

		return mapper.resetPw(userInfo);
	}

	@Override
	public int deleteUser(String u_id) {

		log.info("deleteUser.....................");

		return mapper.deleteUser(u_id);
	}

	@Override
	public int idCheck(String u_id) {

		log.info("idCheck........................");

		return mapper.idCheck(u_id);
	}

	@Override
	public String findId(UserInfoVO userInfo) {

		log.info("findId........................");

		return mapper.getId(userInfo);
	}

}
