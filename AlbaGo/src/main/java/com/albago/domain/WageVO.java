package com.albago.domain;

import lombok.Data;

@Data
public class WageVO {
	private int w_code;
	private long c_code;
	private String u_id;
	private String w_month;
	private double w_base;
	private double w_wextra;
	private double w_nextra;
	private double w_oextra;
	private double w_hextra;
}
