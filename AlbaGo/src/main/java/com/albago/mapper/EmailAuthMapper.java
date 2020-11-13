package com.albago.mapper;

import com.albago.domain.EmailAuthVO;

public interface EmailAuthMapper {

//	public String getOracleSysDate();

	public int create(EmailAuthVO emailAuth);

	public int match(EmailAuthVO emailAuth);

}
