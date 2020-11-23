package com.albago.domain;

import lombok.Data;

@Data
public class HistoryVO {
	private int h_code;
	private long c_code;
	private String u_id;
	private String h_rdate;
	private String h_edate;
	private String h_qdate;
	private char h_position;
	private int h_perhour;
}
