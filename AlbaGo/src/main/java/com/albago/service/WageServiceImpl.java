package com.albago.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.WageVO;
import com.albago.mapper.WageMapper;

import jdk.internal.jline.internal.Log;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class WageServiceImpl implements WageService {

	private WageMapper mapper;

	@Override
	public List<WageVO> getWageList(WageVO wage) {
		log.info("getWageList");

		List<WageVO> list = mapper.getWageList(wage);

		for (WageVO wageVO : list) {
			int ph = mapper.getPay(wage);

			wageVO.setW_base(wageVO.getW_base() * ph);
			wageVO.setW_wextra(wageVO.getW_wextra() * ph);
			wageVO.setW_nextra(wageVO.getW_nextra() * ph * 0.5);
			wageVO.setW_oextra(wageVO.getW_oextra() * ph * 0.5);
			wageVO.setW_hextra(wageVO.getW_hextra() * ph * 0.5);
		}

		return list;
	}

//	@Scheduled()
	public int convertSalaryToWage() {
		Log.info("insertMonthly..................");

		return 0;
	}

}
