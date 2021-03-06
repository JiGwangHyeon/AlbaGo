package com.albago.domain;

import lombok.Data;

@Data
public class ScheduleVO {
	private int s_code;
	private long c_code;
	private String u_id;
	private String s_start;
	private String s_end;
	private String s_arrive;
	private String s_leave;
	private int sr_code;
	private char s_stat;
}
