package com.albago.mapper;

import java.util.List;

import com.albago.domain.CompanyVO;

public interface CompanyMapper {

	public String getCompanyName(long c_code);

	public List<CompanyVO> getCompanyListById(String u_id);

	public CompanyVO selectSingleByCcode(long c_code);

	public int getEday(long c_code);
}
