package com.ssafy.enjoytrip.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		return userMapper.login(userDto);
	}
	
	@Override
	public UserDto userInfo(String userId) throws Exception {
		return userMapper.userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
	}

	@Override
	public void modifyUserInfo(Map<String, String> map) throws Exception {
		userMapper.modifyUserInfo(map);
	}

	@Override
	public void registerUser(Map<String, String> map) throws Exception {
		userMapper.registerUser(map);
	}

}
