package com.albago.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.UserInfoVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class UserInfoMapperTests {

	@Setter(onMethod_ = @Autowired)
	private UserInfoMapper mapper;

//	@Test
	public void testInsert() {
		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_id("testid11111111");
		userInfo.setU_pw("testpwdsdf");
		userInfo.setU_name("testname");
		userInfo.setU_email("testemail");
		userInfo.setU_birth("1994-08-18");
		userInfo.setU_gender(1);
		userInfo.setU_addr("testaddr");
		userInfo.setU_phone("testphone");
		userInfo.setU_position('E');
		log.info(userInfo);
		mapper.insertUser(userInfo);
	}

//	@Test
	public void testGet() {
		log.info("testGet()................");
		mapper.readUser("idtest");
	}

//	@Test
	public void testLogin() {
		log.info("login................");

		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_id("kwang2951");
		userInfo.setU_pw("940818");

		log.info("login_result: " + mapper.login(userInfo));
	}

//	@Test
	public void testMatchNameEmail() {
		log.info("MatchNameEmail....................");

		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_name("지광현");
		userInfo.setU_email("rhkd2951@naver.com");

		log.info("MatchNameEmail_result: " + mapper.matchNameEmail(userInfo));
	}

//	@Test
	public void testMatchNameIdEmail() {
		log.info("MatchNameIdEmail.........");

		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_name("지광현");
		userInfo.setU_id("rhkd2951");
		userInfo.setU_email("rhkd2951@naver.com");

		log.info("MatchNameIdEmail_result: " + mapper.matchNameIdEmail(userInfo));
	}

//	@Test
	public void testResetPw() {
		log.info("ResetPW..........");

		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_id("rhkd2951");
		userInfo.setU_pw(";w3oirjkdshf");

		log.info("ResetPW_result: " + mapper.resetPw(userInfo));
	}

//	@Test
	public void testDeleteUser() {
		log.info("deleteUser.............");

		log.info("deleteUser_result: " + mapper.deleteUser("testid111111"));

	}

	@Test
	public void testIdCheck() {
		log.info("idCheck................");

		log.info("idCheck_result: " + (mapper.idCheck("gkdud0941") == 1 ? "아이디 이미 존재." : "아이디 사용 가능."));
	}
}
