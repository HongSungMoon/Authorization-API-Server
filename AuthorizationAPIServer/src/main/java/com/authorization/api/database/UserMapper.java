package com.authorization.api.database;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.authorization.api.model.UserInfo;

@Mapper
public interface UserMapper {

	public UserInfo getUser(Map<String, String> map);
	
	public void insertUser(UserInfo userInfo);
	
	public UserInfo getUserByID(String id);

	public void modifyUser(UserInfo userInfo);

}
