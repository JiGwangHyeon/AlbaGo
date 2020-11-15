package com.albago.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albago.domain.NoticeVO;
import com.albago.service.NoticeService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/notice")
@Log4j
@AllArgsConstructor
public class NoticeController {

	private NoticeService noticeService;

	@RequestMapping(value = "/getList/{c_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public List<NoticeVO> getList(@PathVariable("c_code") int c_code) {
		log.info("getNoticeList 호출...................");
		log.info("c_code: " + c_code);

		List<NoticeVO> list = noticeService.getList(c_code);

		return list;
	}
}
