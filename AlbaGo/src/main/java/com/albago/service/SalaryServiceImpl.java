package com.albago.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.SalaryVO;
import com.albago.domain.ScheduleVO;
import com.albago.mapper.SalaryMapper;
import com.albago.mapper.ScheduleMapper;

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

		}
	}

}
