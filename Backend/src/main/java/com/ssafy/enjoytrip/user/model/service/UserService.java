package com.ssafy.enjoytrip.user.model.service;

import java.util.Map;

import com.ssafy.enjoytrip.user.model.UserDto;

public interface UserService {

	UserDto login(UserDto userDto) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	void modifyUserInfo(Map<String, String> map) throws Exception;
	void registerUser(Map<String, String> map) throws Exception;
	
}
