package com.albago.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.WageVO;
import com.albago.mapper.WageMapper;

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
			wageVO.setW_base(wageVO.getW_base());
			wageVO.setW_wextra(wageVO.getW_wextra());
			wageVO.setW_nextra(wageVO.getW_nextra());
			wageVO.setW_oextra(wageVO.getW_oextra());
			wageVO.setW_hextra(wageVO.getW_hextra());
		}

		return list;
	}

	@Override
	public int getPay(WageVO wage) {
		log.info("getPay.....");

		return mapper.getPay(wage);
	}

//	@Scheduled()
	public int convertSalaryToWage() {
		log.info("insertMonthly..................");

		return 0;
	}

}
