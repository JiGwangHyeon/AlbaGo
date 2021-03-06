package com.albago.domain;

import lombok.Data;

@Data
public class ScheduleRepeatDTO {

	private int sr_code;
	private long c_code;
	private String u_id;
	private String sr_start;
	private String sr_end;
	private String sr_repeat;
	private char sr_stat;
	private String u_name;
	private String startDate;

}
