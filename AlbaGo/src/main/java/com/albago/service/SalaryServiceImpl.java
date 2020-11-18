package com.albago.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.SalaryVO;
import com.albago.domain.ScheduleVO;
import com.albago.mapper.SalaryMapper;
import com.albago.mapper.ScheduleMapper;
import com.albago.util.CalcDate;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class SalaryServiceImpl implements SalaryService {

	private SalaryMapper salaryMapper;
	private ScheduleMapper scheduleMapper;

	@Override
	public int insertSalary(SalaryVO salary) {
		log.info("insertSalary.....................");

		return salaryMapper.insertSalary(salary);
	}

	public void convertScheduleToSalary() {
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

	public void setWeeklyExtra() {
		List<SalaryVO> list = salaryMapper.getWeeklySumOfBase();

		for (SalaryVO sa : list) {
			double we = CalcDate.getWeeklyExtra(sa.getSa_base(), sa.getS_code());
			sa.setSa_wextra(we);
			salaryMapper.setWeeklyExtra(sa);
		}
	}

}
