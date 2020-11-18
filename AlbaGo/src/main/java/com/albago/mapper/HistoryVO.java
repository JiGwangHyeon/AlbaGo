package com.albago.mapper;

import lombok.Data;

@Data
public class HistoryVO {
	private int h_code;
	private int c_code;
	private String u_id;
	private String h_rdate;
	private String h_edate;
	private String h_qdate;
	private char h_position;
}
