package com.ssafy.enjoytrip.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.user.model.UserDto;

@Mapper
public interface UserMapper {

	UserDto login(UserDto userDto) throws SQLException;
	UserDto userInfo(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	void modifyUserInfo(Map<String, String> map) throws SQLException;
	void registerUser(Map<String, String> map) throws SQLException;
	
}
