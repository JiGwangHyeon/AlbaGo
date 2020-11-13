package com.albago.domain;

import lombok.Data;

@Data
public class EmailAuthVO {

	private String u_email;
	private String a_code;
	private String a_date;

}