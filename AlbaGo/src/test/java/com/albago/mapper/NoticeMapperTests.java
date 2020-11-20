package com.albago.mapper;

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
public class NoticeMapperTests {

	@Setter(onMethod_ = @Autowired)
	private NoticeMapper mapper;

	@Test
	public void testGetList() {
		log.info("testGetList...................");

		mapper.getList(1234567890).forEach(notice -> log.info(notice));
	}
}
