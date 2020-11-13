package com.albago.service;

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
public class EmailAuthServiceTests {

	@Setter(onMethod_ = @Autowired)
	private EmailAuthService service;

	@Test
	public void testCreate() {

		EmailAuthVO emailAuth = new EmailAuthVO();
		emailAuth.setU_email("rhkd2951@n.omdddd");
		emailAuth.setA_code("0818");

		service.makeAuthCode(emailAuth);
	}

}
