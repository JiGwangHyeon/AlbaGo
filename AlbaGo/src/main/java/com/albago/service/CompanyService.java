package com.albago.service;

import java.util.List;

import com.albago.domain.CompanyVO;

public interface CompanyService {

	public String getCompanyName(long c_code);

	public List<CompanyVO> getCompanyListById(String u_id);
}
