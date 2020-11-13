package com.albago.service;

import static org.junit.Assert.assertNotNull;

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
public class UserInfoServiceTests {

	@Setter(onMethod_ = @Autowired)
	private UserInfoService service;

//	@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service);
	}

//	@Test
	public void testRegister() {
		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_id("testid");
		userInfo.setU_pw("testpw");
		userInfo.setU_name("testname");
		userInfo.setU_birth("1994-08-18");
		userInfo.setU_email("testemail");
		userInfo.setU_phone("testphone");
		userInfo.setU_addr("testaddr");
		userInfo.setU_gender('M');
		userInfo.setU_position('E');
		log.info("userInfo: " + userInfo);

		service.register(userInfo);

		log.info("회원가입 완료. 생성된 아이디: " + userInfo.getU_id());
	}

//	@Test
	public void testGet() {
		log.info("userInfo_get..............");

		service.get("idtest");
	}

//	@Test
	public void testLogin() {
		log.info("login...................");

		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_id("testid11111111");
		userInfo.setU_pw("testpwdsdf");

		log.info("login_result: " + service.login(userInfo));
	}

//	@Test
	public void testBeforeFindId() {
		log.info("beforeFindId...............");

		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_name("지광현");
		userInfo.setU_email("rhkd2951@naver.com");

		log.info("beforeFindId_result: " + service.beforeFindId(userInfo));
	}

//	@Test
	public void testBeforeFindPw() {
		log.info("beforeFindPw....................");

		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_name("지광현");
		userInfo.setU_id("kwang2951");
		userInfo.setU_email("rhkd2951@naver.com");

		log.info("beforeFindPw_result: " + service.beforeFindPw(userInfo));
	}

//	@Test
	public void testResetPw() {
		log.info("resetPw...................");

		UserInfoVO userInfo = new UserInfoVO();
		userInfo.setU_id("testid111");
		userInfo.setU_pw("password");

		log.info("resetPw_result: " + service.resetPw(userInfo));
	}

//	@Test
	public void testDeleteUser() {
		log.info("deleteUser...................");

		log.info("deleteUser_result: " + service.deleteUser("testid111"));
	}

	@Test
	public void testIdCheck() {
		log.info("idCheck......................");

		log.info("idCheck_result: " + (service.idCheck("gkdud0941") == 1 ? "아이디 이미 존재함." : "아이디 사용 가능함."));
	}
}
