package com.authorization.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.authorization.api.model.UserInfo;
import com.authorization.api.model.admin.UserList;

public interface UserService {

	public HttpStatus login(UserInfo userInfo);

	public UserInfo join(UserInfo userInfo);

	public void modifyUser(UserInfo userInfo);

	public boolean checkAdmin(UserInfo userInfo);

	public List<UserInfo> getUserList();
	
}
