package com.albago.service;

import java.util.List;

import com.albago.domain.HistoryVO;
import com.albago.domain.WageVO;

public interface WageService {

	public List<WageVO> getWageList(WageVO wage);

	public int getPay(HistoryVO history);

}
