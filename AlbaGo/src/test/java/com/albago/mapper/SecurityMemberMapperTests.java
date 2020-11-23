package com.albago.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.SecurityMemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml" })
@Log4j
public class SecurityMemberMapperTests {

	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder;

	@Setter(onMethod_ = @Autowired)
	private SecurityMemberMapper mapper;

//	@Test
	public void testInsert() {

		SecurityMemberVO vo = new SecurityMemberVO();
		vo.setId("rhkd29511");
		vo.setPw(pwencoder.encode("9408d18"));

		mapper.insert(vo);

	}

	@Test
	public void testSelect() {

		SecurityMemberVO vo = new SecurityMemberVO();

		vo = mapper.select("rhkd2951");

		if (pwencoder.matches("940818", vo.getPw())) {
			log.info(vo.getPw());
		}

//		String encodedPw = mapper.select("rhkd2951");

	}
}
