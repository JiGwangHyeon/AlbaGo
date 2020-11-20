package com.albago.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.NoticeVO;
import com.albago.mapper.NoticeMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class NoticeServiceImpl implements NoticeService {

	private NoticeMapper mapper;

	@Override
	public List<NoticeVO> getList(long c_code) {
		log.info("getNoticeList..................");

		return mapper.getList(c_code);
	}

}
