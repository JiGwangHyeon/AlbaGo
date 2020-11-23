package com.albago.mapper;

import com.albago.domain.SecurityMemberVO;

public interface SecurityMemberMapper {

	public int insert(SecurityMemberVO securityMember);

	public SecurityMemberVO select(String id);
}
