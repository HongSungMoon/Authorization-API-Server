package com.authorization.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.authorization.api.database.UserMapper;
import com.authorization.api.model.UserInfo;
import com.authorization.api.service.UserService;
import com.authorization.api.utils.CryptoUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	private CryptoUtil cryptoUtil = new CryptoUtil();

	@Override
	public HttpStatus login(UserInfo userInfo) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", userInfo.getId());
		map.put("password", userInfo.getPassword());
		
		UserInfo result = userMapper.getUser(map);
		
		if(result == null) 
			return HttpStatus.UNAUTHORIZED;
		return HttpStatus.OK;
		
	}

	@Override
	public UserInfo join(UserInfo userInfo) {
		
		UserInfo result = userMapper.getUserByID(userInfo.getId());
		
		if(result == null) {
			userMapper.insertUser(userInfo);
		}
		return result;
	}

	@Override
	public void modifyUser(UserInfo userInfo) {
		String salt = cryptoUtil.randomKey(10);
		String newPasswd = cryptoUtil.sha256(salt + userInfo.getPassword());
		userInfo.setSalt(salt);
		userInfo.setPassword(newPasswd);
		userMapper.modifyUser(userInfo);
	}

}
