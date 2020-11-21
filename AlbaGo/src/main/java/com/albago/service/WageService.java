package com.albago.service;

import java.util.List;

import com.albago.domain.WageVO;

public interface WageService {

	public List<WageVO> getWageList(WageVO wage);

	public int getPay(WageVO wage);

}
