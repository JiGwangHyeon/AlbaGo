package com.albago.mapper;

import java.util.List;

import com.albago.domain.CompanyVO;

public interface CompanyMapper {

	public String getCompanyName(int c_code);

	public List<CompanyVO> getCompanyListById(String u_id);
}
