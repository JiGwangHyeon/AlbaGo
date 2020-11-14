package com.albago.mapper;

import java.util.List;

import com.albago.domain.NoticeVO;

public interface NoticeMapper {

	public List<NoticeVO> getList(int c_code);
}
