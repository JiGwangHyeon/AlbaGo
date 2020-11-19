package com.albago.domain;

import lombok.Data;

@Data
public class CompanyVO {

	private long c_code;
	private String c_name;
	private String c_addr;
	private String c_owner;
	private String c_phone;
	private int c_pday;
	private int c_eday;
	private String c_type;
}
