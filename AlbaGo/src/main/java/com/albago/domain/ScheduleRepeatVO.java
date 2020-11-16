package com.albago.domain;

import lombok.Data;

@Data
public class ScheduleRepeatVO {

	private int sr_code;
	private int c_code;
	private String u_id;
	private String sr_start;
	private String sr_end;
	private String sr_repeat;
}
