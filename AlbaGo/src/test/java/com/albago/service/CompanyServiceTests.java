package com.albago.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class CompanyServiceTests {

	@Setter(onMethod_ = @Autowired)
	private CompanyService service;

	@Test
	public void testGetCompanyName() {
		log.info("testGetCompanyName...................");

		log.info("CompanyName: " + service.getCompanyName(1234123521));
	}
}
