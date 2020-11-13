package com.albago.domain;

import lombok.Data;

@Data
public class UserInfoVO {

	private String u_id;
	private String u_pw;
	private String u_name;
	private String u_email;
	private String u_birth;
	private int u_gender;
	private String u_addr;
	private String u_phone;
	private char u_position;
	private String u_rdate;
	private String u_qdate;
}