package com.albago.mapper;

import java.util.List;

import com.albago.domain.WageVO;

public interface WageMapper {
	public int inserMonthly();

	public List<WageVO> getWageList(WageVO wage);

	public int getPay(WageVO wage);
}
