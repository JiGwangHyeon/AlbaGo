package com.albago.service;

import com.albago.domain.EmailAuthVO;

public interface EmailAuthService {

	public int makeAuthCode(EmailAuthVO emailAuth);

	public int matchCode(EmailAuthVO emailAuth);

}
