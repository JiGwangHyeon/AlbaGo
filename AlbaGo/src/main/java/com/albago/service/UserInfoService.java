package com.albago.service;

import com.albago.domain.UserInfoVO;

public interface UserInfoService {

	public int register(UserInfoVO userInfo);

	public UserInfoVO get(String u_id);

	public int login(UserInfoVO userInfo);

	public int beforeFindId(UserInfoVO userInfo);

	public int beforeFindPw(UserInfoVO userInfo);

	public int resetPw(UserInfoVO userInfo);

	public int deleteUser(String u_id);

	public int idCheck(String u_id);

	public String findId(UserInfoVO userInfo);

	public int editProfile(UserInfoVO userInfo);
	
	public int isPwCorrect(UserInfoVO userInfo);
}
