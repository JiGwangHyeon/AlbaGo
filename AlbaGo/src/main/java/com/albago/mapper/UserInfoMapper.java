package com.albago.mapper;

import com.albago.domain.UserInfoVO;

public interface UserInfoMapper {
	public int insertUser(UserInfoVO userInfo);

	public UserInfoVO readUser(String u_id);

	public int login(UserInfoVO userInfo);

	public int matchNameEmail(UserInfoVO userInfo);

	public int matchNameIdEmail(UserInfoVO userInfo);

	public int resetPw(UserInfoVO userInfo);

	public int deleteUser(String u_id);

	public int idCheck(String u_id);

	public String getId(UserInfoVO userInfo);
}
