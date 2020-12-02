package com.albago.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
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
public class SalaryServiceTests {

	@Setter(onMethod_ = @Autowired)
	private SalaryService salaryService;
	@Setter(onMethod_ = @Autowired)
	private SalaryServiceImpl salaryServiceImpl;

	@Setter(onMethod_ = @Autowired)
	private ScheduleService scheduleService;

//	@Test
	public void testInsert() {
		log.info("testInsert......................");

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

		log.info("insertResult: " + salaryService.insertSalary(salary));
	}

//	@Test
	public void testcalcdate() {
		log.info("testCalc..................");

		ScheduleVO schedule = new ScheduleVO();
		schedule.setS_code(154);
		schedule.setU_id("gkdud0941");
		schedule.setC_code(1234123521);
		schedule.setS_start("2020-11-15 09:00:00");
		schedule.setS_end("2020-11-15 18:00:00");
		schedule.setS_arrive("2020-11-15 08:50:00");
		schedule.setS_leave("2020-11-15 18:10:00");
		schedule.setSr_code(51);

		Calendar[] st = CalcDate.getStandardTime(schedule);

		SalaryVO salary = new SalaryVO();
		salary.setSa_code(3);
		salary.setC_code(1234123521);
		salary.setU_id("gkdud0941");
		salary.setS_code(10);

		log.info(st[0].getTime());
		log.info(st[1].getTime());
		double base = CalcDate.getBase(st);
		log.info("base: " + base);
		log.info("night: " + CalcDate.getNightExtra(st));
		log.info("over: " + CalcDate.getOverExtra(base));
		log.info("holiday: " + CalcDate.getHolidayExtra(st, base));

	}

//	@Test
	public void testconvert() {
		salaryServiceImpl.convertScheduleToSalaryForDummy();
	}

	@Test
	public void dummy() {
		Calendar cal = Calendar.getInstance();

		cal.set(2019, 11, 30);

		Calendar cal2 = (Calendar) cal.clone();
		cal2.set(2020, 10, 30);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		while (cal2.after(cal)) {
			salaryServiceImpl.setWeeklyExtra(sdf.format(cal.getTime()));

			cal.add(Calendar.DATE, 7);
		}
	}
}
