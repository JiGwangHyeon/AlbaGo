package com.albago.domain;

import lombok.Data;

@Data
public class EmailDTO {

	private String senderName;
	private String senderMail;
	private String receiveMail;
	private String subject;
	private String message;

	@Override
	public String toString() {
		return "EmailDTO [senderName=" + senderName + ", senderMail=" + senderMail + ", receiveMail=" + receiveMail
				+ ", subject=" + subject + ", message=" + message + "]";
	}
}
