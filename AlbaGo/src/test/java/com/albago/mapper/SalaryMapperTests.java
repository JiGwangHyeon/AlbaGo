package com.albago.mapper;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albago.domain.SalaryVO;
import com.albago.domain.ScheduleVO;
import com.albago.util.CalcDate;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SalaryMapperTests {

	@Setter(onMethod_ = @Autowired)
	private SalaryMapper salaryMapper;

	@Setter(onMethod_ = @Autowired)
	private ScheduleMapper scheduleMapper;

//	@Test
	public void testInsert() {
		log.info("SalaryInsertTest..................");

		SalaryVO salary = new SalaryVO();
		salary.setSa_code(3);
		salary.setC_code(1234123521);
		salary.setU_id("gkdud0941");
		salary.setS_code(10);
		salary.setSa_base(8);
		salary.setSa_hextra(0);
		salary.setSa_wextra(0);
		salary.setSa_nextra(0);
		salary.setSa_oextra(0);

		log.info("insertResult: " + salaryMapper.insertSalary(salary));
	}

//	@Test
	public void testGetWeeklySumOfBase() {
		log.info("testGetWeeklySumOfBase............");

		log.info("result: " + salaryMapper.getWeeklySumOfBase());
	}

	/*
	 * @Test public void testSetWeeklyExtra() {
	 * log.info("testSetWeeklyExtra...................."); SimpleDateFormat sdf =
	 * new SimpleDateFormat("yyyyMMdd"); Calendar date = Calendar.getInstance();
	 * date.set(2020, 5, 9); Calendar now = Calendar.getInstance(); while
	 * (now.after(date)) { String dateString = sdf.format(date.getTime());
	 * log.fatal("dateString: " + dateString); List<SalaryVO> list =
	 * salaryMapper.getWeeklySumOfBase(dateString); log.info("weeklySumOfBase: " +
	 * list);
	 * 
	 * for (SalaryVO sa : list) { sa.setTest(dateString); double we =
	 * CalcDate.getWeeklyExtra(sa.getSa_base(), sa.getS_code());
	 * sa.setSa_wextra(we); salaryMapper.setWeeklyExtra(sa); }
	 * date.add(Calendar.DATE, 7); } }
	 */

//	@Test
	public void test() {
		List<ScheduleVO> schedule = scheduleMapper.getListTwoDaysAgo();

		for (ScheduleVO sc : schedule) {
			SalaryVO salary = new SalaryVO();
			salary.setC_code(sc.getC_code());
			salary.setU_id(sc.getU_id());
			salary.setS_code(sc.getS_code());
			CalcDate.setSalaryVO(salary, sc);
			salaryMapper.insertSalary(salary);
		}
	}
}
