package com.authorization.api.service;

import org.springframework.http.HttpStatus;

import com.authorization.api.model.UserInfo;

public interface UserService {

	public HttpStatus login(UserInfo userInfo);

	public UserInfo join(UserInfo userInfo);

	public void modifyUser(UserInfo userInfo);
	
}
