package com.albago.service;

import java.util.List;

import com.albago.domain.NoticeVO;

public interface NoticeService {

	public List<NoticeVO> getList(long c_code);
}
