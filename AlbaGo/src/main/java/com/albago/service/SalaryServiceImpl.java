package com.albago.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.albago.domain.SalaryVO;
import com.albago.domain.ScheduleVO;
import com.albago.domain.WageVO;
import com.albago.mapper.CompanyMapper;
import com.albago.mapper.SalaryMapper;
import com.albago.mapper.ScheduleMapper;
import com.albago.util.CalcDate;
import com.albago.util.ForLog;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class SalaryServiceImpl implements SalaryService {

	private SalaryMapper salaryMapper;
	private ScheduleMapper scheduleMapper;

	private CompanyMapper companyMapper;

	@Override
	public int insertSalary(SalaryVO salary) {
		log.info("insertSalary.....................");

		return salaryMapper.insertSalary(salary);
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void convertScheduleToSalary() {
		List<ScheduleVO> schedule = scheduleMapper.selectMultiByStartIsTwoDayAgo();

		for (ScheduleVO sc : schedule) {
			if (sc.getS_arrive().trim().isEmpty() || sc.getS_leave().trim().isEmpty()) {
				continue;
			}
			SalaryVO salary = new SalaryVO();
			salary.setC_code(sc.getC_code());
			salary.setU_id(sc.getU_id());
			salary.setS_code(sc.getS_code());
			CalcDate.setSalaryVO(salary, sc);
			salaryMapper.insertSalary(salary);
		}
	}

//	@Scheduled(cron = "0 0 0 ? * 3")
	public void setWeeklyExtra(String date) {
		List<SalaryVO> list = salaryMapper.getWeeklySumOfBase(date);

		for (SalaryVO sa : list) {
			double we = CalcDate.getWeeklyExtra(sa.getSa_base(), sa.getS_code());
			sa.setDate(date);
			sa.setSa_wextra(we);
			salaryMapper.setWeeklyExtra(sa);
		}
	}

	public WageVO getExpectedWage(ScheduleVO schedule) {

		log.info("getExpectedWage" + ForLog.dot);

		Calendar sCal = Calendar.getInstance();

		sCal.set(Calendar.HOUR_OF_DAY, 0);
		sCal.set(Calendar.MINUTE, 0);
		sCal.set(Calendar.SECOND, 0);

		int eday = companyMapper.getEday(schedule.getC_code());
		int today = sCal.get(Calendar.DATE);

		sCal.set(Calendar.DATE, eday + 1);
		Calendar eCal = (Calendar) sCal.clone();
		eCal.add(Calendar.MONTH, 1);

		if (today <= eday) {
			sCal.add(Calendar.MONTH, -1);
			eCal.add(Calendar.MONTH, -1);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		schedule.setS_start(sdf.format(sCal.getTime()));
		schedule.setS_end(sdf.format(eCal.getTime()));

		SalaryVO salarySum = new SalaryVO();

		List<ScheduleVO> list = scheduleMapper.selectMultiForExpect(schedule);

		int count = 0;

		for (ScheduleVO sc : list) {
			sc.setS_arrive(sc.getS_start());
			sc.setS_leave(sc.getS_end());
			SalaryVO salary = new SalaryVO();
			salary.setC_code(sc.getC_code());
			salary.setU_id(sc.getU_id());
			salary.setS_code(sc.getS_code());
			CalcDate.setSalaryVO(salary, sc);
			salarySum.setSa_base(salarySum.getSa_base() + salary.getSa_base());
			salarySum.setSa_nextra(salarySum.getSa_nextra() + salary.getSa_nextra());
			salarySum.setSa_oextra(salarySum.getSa_oextra() + salary.getSa_oextra());
			salarySum.setSa_hextra(salarySum.getSa_hextra() + salary.getSa_hextra());
			count += 1;
		}
		salarySum.setSa_wextra(salarySum.getSa_base() / 5);

		WageVO wage = new WageVO();

		wage.setC_code(schedule.getC_code());
		wage.setU_id(schedule.getU_id());
		wage.setW_base(salarySum.getSa_base());
		wage.setW_hextra(salarySum.getSa_hextra());
		wage.setW_nextra(salarySum.getSa_nextra());
		wage.setW_oextra(salarySum.getSa_oextra());
		wage.setW_wextra(salarySum.getSa_wextra());
		wage.setW_month(Integer.toString(eCal.get(Calendar.YEAR)) + Integer.toString(eCal.get(Calendar.MONTH) + 1));

		return wage;
	}

	public void convertScheduleToSalaryForDummy() {
		List<ScheduleVO> schedule = scheduleMapper.selectDummy();

		for (ScheduleVO sc : schedule) {
			if (sc.getS_arrive().trim().isEmpty() || sc.getS_leave().trim().isEmpty()) {
				continue;
			}
			SalaryVO salary = new SalaryVO();
			salary.setC_code(sc.getC_code());
			salary.setU_id(sc.getU_id());
			salary.setS_code(sc.getS_code());
			CalcDate.setSalaryVO(salary, sc);
			salaryMapper.insertSalary(salary);
		}
	}

}
