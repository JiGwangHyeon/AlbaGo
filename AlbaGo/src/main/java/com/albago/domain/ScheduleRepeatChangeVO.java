package com.albago.domain;

import lombok.Data;

@Data
public class ScheduleRepeatChangeVO {

	private int src_code;
	private int sr_code;
	private String src_start;
	private String src_end;
	private String src_repeat;
	private String src_reason;
	private String src_reply;
	private String src_wdate;
	private String src_udate;
	private char src_stat;

}
