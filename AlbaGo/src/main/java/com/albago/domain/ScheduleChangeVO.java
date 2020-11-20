package com.albago.domain;

import lombok.Data;

@Data
public class ScheduleChangeVO {

	private int sc_code;
	private int s_code;
	private String sc_start;
	private String sc_end;
	private String sc_reason;
	private String sc_reply;
	private String sc_wdate;
	private String sc_udate;
	private char sc_stat;
}
