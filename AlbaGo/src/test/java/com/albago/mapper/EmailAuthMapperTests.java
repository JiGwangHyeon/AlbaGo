package com.albago.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.EmailAuthVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class EmailAuthMapperTests {

	@Setter(onMethod_ = @Autowired)
	private EmailAuthMapper mapper;

	@Test
	public void testCreate() {
		log.info("testCreate.................");

		EmailAuthVO emailAuth = new EmailAuthVO();
		emailAuth.setU_email("rhkd2951@n.omdddd");
		emailAuth.setA_code("0818");

		log.info("result: " + mapper.create(emailAuth));
	}

//	@Test
	public void testMatch() {
		log.info("testMatch..................");

		EmailAuthVO emailAuth = new EmailAuthVO();
		emailAuth.setU_email("rhkd2951@naver.com");
		emailAuth.setA_code("0818");

		log.info("result: " + mapper.match(emailAuth));
	}
}
