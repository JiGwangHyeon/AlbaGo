package com.albago.domain;

import lombok.Data;

@Data
public class SalaryVO {

	private int sa_code;
	private long c_code;
	private String u_id;
	private int s_code;
	private double sa_base;
	private double sa_wextra;
	private double sa_nextra;
	private double sa_oextra;
	private double sa_hextra;
}
