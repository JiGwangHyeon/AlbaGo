package com.albago.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.WageVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class WageMapperTests {

	@Setter(onMethod_ = @Autowired)
	private WageMapper mapper;

//	@Test
	public void dummy() {

		WageVO wage = new WageVO();
		for (int i = 3; i < 10; i++) {
			wage.setW_month("20200" + i + "29");

			mapper.insertDummy(wage);
		}
		for (int i = 10; i < 11; i++) {
			wage.setW_month("2020" + i + "29");

			mapper.insertDummy(wage);
		}

	}

	@Test
	public void dummytest() {
		WageVO wage = new WageVO();
		wage.setW_month("20201229");
		mapper.insertDummyThisMonth(wage);
	}
}
