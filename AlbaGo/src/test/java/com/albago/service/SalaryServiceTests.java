package com.albago.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.SalaryVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SalaryServiceTests {

	@Setter(onMethod_ = @Autowired)
	private SalaryService service;

	@Test
	public void testInsert() {
		log.info("testInsert......................");

		SalaryVO salary = new SalaryVO(2, 1234123521, "gkdud0941", 10, 8, 0, 0, 0, 0);

		log.info("insertResult: " + service.insertSalary(salary));
	}
}
