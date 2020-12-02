package com.albago.mapper;

import java.util.List;

import com.albago.domain.WageVO;

public interface WageMapper {
	public int inserMonthly();

	public List<WageVO> getWageList(WageVO wage);

	public int getPay(WageVO wage);

	public int selectSingle(WageVO wage);

	public int insertDummy(WageVO wage);

	public int insertDummyThisMonth(WageVO wage);
}
