package com.albago.mapper;

import java.util.List;

import com.albago.domain.SalaryVO;

public interface SalaryMapper {

	public int insertSalary(SalaryVO salary);

	public List<SalaryVO> getWeeklySumOfBase(String date);

	public int setWeeklyExtra(SalaryVO salary);
}
