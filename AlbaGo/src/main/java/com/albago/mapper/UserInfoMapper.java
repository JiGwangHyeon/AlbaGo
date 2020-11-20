package com.albago.mapper;

import com.albago.domain.UserInfoVO;

public interface UserInfoMapper {

	public int insert(UserInfoVO userInfo);

	public UserInfoVO selectSingle(String u_id);

	public String selectIdByNameEmail(UserInfoVO userInfo);

	public int getCountById(String u_id);

	public int getCountByIdPw(UserInfoVO userInfo);

	public int getCountByNameEmail(UserInfoVO userInfo);

	public int getCountByIdNameEmail(UserInfoVO userInfo);

	public int updatePw(UserInfoVO userInfo);

	public int updateAddr(UserInfoVO userInfo);

	public int updatePhone(UserInfoVO userInfo);

	public int delete(String u_id);
}
