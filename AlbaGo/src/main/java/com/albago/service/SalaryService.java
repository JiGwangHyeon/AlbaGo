package com.albago.service;

import com.albago.domain.SalaryVO;
import com.albago.domain.ScheduleVO;
import com.albago.domain.WageVO;

public interface SalaryService {

	public int insertSalary(SalaryVO salary);

	public WageVO getExpectedWage(ScheduleVO Schedule);
}
