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

		return mapper.insert(userInfo);
	}

	@Override
	public UserInfoVO get(String u_id) {

		log.info("userInfo_get..........." + u_id);

		return mapper.selectSingle(u_id);
	}

	@Override
	public int login(UserInfoVO userInfo) {

		log.info("login.....................");

		return mapper.getCountByIdPw(userInfo);
	}

	@Override
	public int beforeFindId(UserInfoVO userInfo) {

		log.info("beforeFindId.....................");

		return mapper.getCountByNameEmail(userInfo);
	}

	@Override
	public int beforeFindPw(UserInfoVO userInfo) {

		log.info("beforeFindPw.....................");

		return mapper.getCountByIdNameEmail(userInfo);
	}

	@Override
	public int resetPw(UserInfoVO userInfo) {

		log.info("resetPw.....................");

		return mapper.updatePw(userInfo);
	}

	@Override
	public int deleteUser(String u_id) {

		log.info("deleteUser.....................");

		return mapper.delete(u_id);
	}

	@Override
	public int idCheck(String u_id) {

		log.info("idCheck........................");

		return mapper.getCountById(u_id);
	}

	@Override
	public String findId(UserInfoVO userInfo) {

		log.info("findId........................");

		return mapper.selectIdByNameEmail(userInfo);
	}

	@Override
	public int editProfile(UserInfoVO userInfo) {

		log.info("editProfile.......................");

		int count = 0;

		if (!userInfo.getU_pw().trim().isEmpty())
			count += mapper.updatePw(userInfo);
		if (!userInfo.getU_addr().trim().isEmpty())
			count += mapper.updateAddr(userInfo);
		if (!userInfo.getU_phone().trim().isEmpty())
			count += mapper.updatePhone(userInfo);

		return count;
	}

	@Override
	public int isPwCorrect(UserInfoVO userInfo) {
		
		log.info("isPwCorrect.......................");
		
		return mapper.isPwCorrect(userInfo);
	}
}
